/**
 * Created by dort on 20.12.16.
 */

var basic_part_url = 'http://localhost:8080';
var userId = undefined;
var shelves;
var books;
var openBook = {};
var currentPage = 1;
var totalPage = 1;
var openBookId = '';
localStorage.clear();

$(document).ready(function () {

    $("#auth-btn").click(function () {

        var login = $("#login");
        var password = $("#password");
        if (login != ''  || password != '' ) {
            $.ajax({
                url: "login",
                method: "get",
                data: {'username': login.val(), 'password': password.val()},
                success: function (data) {
                    var result = JSON.parse(data);
                    userId = result.user_id;
                    localStorage.setItem("userId",userId);
                    get_shelves(update_shelves);
                    get_allowed_books(update_books);
                    location.href = result.nextPage;
                },
                error: function () {
                    alert("Uncorrect password or login. May be you don't confirm account ? ");
                }
            });
        } else {
            alert("Please input correct login");
        }
    });

    $("#reg-btn").click(function () {
        localStorage.clear();
        //проверим капчу
        var captchaResponse = grecaptcha.getResponse();
        var login = $("#login");
        var password = $("#password");
        var email = $("#email");

        if(login != '' && password != '' && email != ''){
            $.ajax({
                url: "/registration",
                method: "post",
                // crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },
                data: {'username': login.val(), 'password': password.val(),'email': email.val(), 'recaptcha': captchaResponse},
                success: function () {
                    $("#login").val('');$("#password").val(''); $("#email").val('');grecaptcha.reset();

                    alert("Please ! Confirm registration by email");
                },
                error: function (data) {
                    alert("Error ! What happened ?");
                }
            });
        }
    });

    $("#btn-add-shelf").click(function () {
        var shelf = $("#shelf");

        if (shelf != '') {
            $.ajax({
                url: "/shelf/save",
                method: "post",
                data: {
                    'name': shelf.val()
                },
                success: function () {
                    get_shelves(update_shelves);
                }
            });
        } else {
            alert("Empty name shelf");
        }
    });

    function get_shelves(callback){
        $.ajax({
            url: "/shelf/all",
            method: "get",
            success: function (data) {
                shelves = data;
                if(callback != undefined){
                    callback(data);
                }
            }
        });
    }

    function get_allowed_books(callback){
        userId = localStorage.getItem("userId");
        $.ajax({
            url: "/book/get-allowed-book",
            data: {
                'userId': userId
            },
            method: "get",
            success: function (data) {
                books = data;
                if(callback != undefined){
                    callback(data);
                }
            }
        });
    }

    function update_shelves(data){
        var result = data;
        var optional = "<table>";
        for (var i = 0; i != result.length; ++i) {
            optional += "<tr>";
            optional += "<td>"+result[i].name+"</td>";
            var btn_name = "btn_del_shelf_" + result[i].id;
            optional += "<td><button class='btn' id='" + btn_name+ "' type='button'>Удалить</button></td>";
            var btndel = $("#"+btn_name);
            btndel.click(function () {
                deleteShelf(result[i].id);
            });
            optional += "</tr>";
        }
        optional+="</table>";
        tbody_shelves = $("#tbody_shelf");
        tbody_shelves.html(optional);

        for (var i = 0; i != result.length; ++i) {
            var btn_name = "btn_del_shelf_" + result[i].id;
            var btndel = $("#"+btn_name);
            btndel.bind("click",{ param: result[i].id },deleteShelf);
        }

        var optional = "";
        for (var i = 0; i != shelves.length; ++i) {
            optional += "<option value = "+shelves[i].id+">" + shelves[i].name + "</option>";
        }
        var dropdownMenu = document.getElementById("shelf_select-btn");
        dropdownMenu.innerHTML = optional;

        var dropdownMenu = document.getElementById("shelf_select-btn2");
        dropdownMenu.innerHTML = optional;

        var dropdownMenu = document.getElementById("shelf_select-btn3");
        dropdownMenu.innerHTML = optional;
    }

    function deleteShelf(event){
        var id = event.data.param;
        $.ajax({
            url: "/shelf/delete",
            method: "post",
            data: {'id': id},
            success:function () {
                alert("Success delete shelf");
                get_shelves(update_shelves);
            }
        });
    }

    function update_books(data){
        userId = localStorage.getItem("userId");
        var result = data;
        var optional = "<table>";
        for (var i = 0; i != result.length; ++i) {
            optional += "<tr>";
            optional += "<td>"+result[i].title+"</td>";
            optional += "<td>"+result[i].author+"</td>";
            optional += "<td>"+result[i].shelf.name+"</td>";
            optional += "<td>"+result[i].owner.name+"</td>";
            var btn_name = "btn_change_shelf_" + result[i].id;
            if(result[i].owner.id != userId){
                optional += "<td><button class='btn' id='" + btn_name+ "' type='button' disabled='disabled'>Изменить</button></td>";
            } else {
                optional += "<td><button class='btn' id='" + btn_name+ "' type='button' >Изменить</button></td>";
            }

            var btn_name = "btn_open_book" + result[i].id;
            optional += "<td><button class='btn' id='" + btn_name+ "' type='button'>Читать</button></td>";
            optional += "</tr>";
        }
        optional+="</table>";
        tbody_book = $("#tbody_book");
        tbody_book.html(optional);


        for (var i = 0; i != result.length; ++i) {
            btn_name = "btn_change_shelf_" + result[i].id;
            var btnchange = $("#"+btn_name);
            btnchange.bind("click",{ param: result[i].id },openModal);
        }

        for (var i = 0; i != result.length; ++i) {
            btn_name = "btn_open_book" + result[i].id;
            var btn_open_book = $("#"+btn_name);
            btn_open_book.bind("click",{ param: result[i].id },openBookForRead);
        }
    }

    function openModal(event){
        var id = event.data.param;

        var c = $('<div class="box-modal" />');
        c.html($('.b-text').html());
        c.prepend('<div class="box-modal_close arcticmodal-close">X</div>');

        $('#changeShelf').arcticmodal({
            closeOnEsc: false,
            closeOnOverlayClick: false,
            content: c,
            overlay: {
                css: {
                    backgroundColor: '#fff',
                    backgroundRepeat: 'repeat',
                    backgroundPosition: '50% 0',
                    opacity: .75
                }
            },
             afterClose: function(data, el) {
                 shelfId = $("#shelf_select-btn3").val();
                 $.ajax({
                     url: "/book/change-shelf",
                     method: "post",
                     data: {'bookId': id, 'shelfId':shelfId},
                     success:function () {
                         get_allowed_books(update_books);
                     }
                 });
            }
        });
    }

    function openBookForRead(event)   {
        userId = localStorage.getItem("userId");
        var id = event.data.param;
        $.ajax({
            url: "/book/open",
            method: "post",
            data: {'bookId': id, 'userId': userId},
            success:function (data) {
                openBook = data;
                currentPage = openBook.currentPage;
                totalPage = openBook.totalPage;
                openBookId = id;
                console.log(currentPage);
                viewCurrentPage(currentPage);
                var pageStyle = $("#generatedStyle");
                pageStyle.html(openBook.style);
            }
        });
    }

    function viewCurrentPage(index) {
        fieldContent = $("#content_book");
        fieldContent.html(openBook.contentPage[index].content);
    }


    $("#btn-add-book").click(function (event) {
        userId = localStorage.getItem("userId");
        event.preventDefault();
        var form = $('#fileUploadForm')[0];
        // Create an FormData object
        var data = new FormData(form);
        data.append("userId" , userId);
        $("#btn-add-book").prop("disabled", true);
        $.ajax({
            url: "/book/save",
            method: "post",
            enctype: 'multipart/form-data',
            contentType: false,
            data: data,
            processData: false,
            success: function () {
                get_allowed_books(update_books);
                $("#btn-add-book").prop("disabled", false);
                form.reset();
            }
        });

    });

    $("#btn-search-book").click(function () {

        var shelf_search = $("#shelf_select-btn2");
        userId = localStorage.getItem("userId");
        if (shelf_search.val()  != undefined && shelf_search.val() != '') {
            $.ajax({
                data: {
                    'userId': userId,
                    'shelfId': shelf_search.val(),
                },
                url: "/book/search-another-people-book",
                method: "get",
                success: function (data) {
                    var result = data;
                    var optional = "<table>";
                    for (var i = 0; i != result.length; ++i) {
                        optional += "<tr>";
                        optional += "<td>"+result[i].title+"</td>";
                        optional += "<td>"+result[i].author+"</td>";
                        var btn_name = "btn_book_read_request" + result[i].id;
                        optional += "<td><button class='btn' id='" + btn_name+ "' type='button'>Запрос на чтение</button></td>";
                    }
                    optional+="</table>";
                    tbody_request_book = $("#tbody_request_book");
                    tbody_request_book.html(optional);



                    for (var i = 0; i != result.length; ++i) {
                        btn_name = "btn_book_read_request" + result[i].id;
                        var btn_book_read_request  = $("#"+btn_name);
                        btn_book_read_request.bind("click",{ param: result[i].id },openModalRequestBook);
                    }
                }
            });
        } else {
            alert("Empty name shelf");
        }
    });

    function openModalRequestBook(event){
        var id = event.data.param;

        var c = $('<div class="box-modal" />');
        c.html($('.b-text').html());
        c.prepend('<div class="box-modal_close arcticmodal-close">X</div>');
        userId = localStorage.getItem("userId");
        $('#requestBook').arcticmodal({
            closeOnEsc: false,
            closeOnOverlayClick: false,
            content: c,
            overlay: {
                css: {
                    backgroundColor: '#fff',
                    backgroundRepeat: 'repeat',
                    backgroundPosition: '50% 0',
                    opacity: .75
                }
            },
            afterClose: function(data, el) {
                requestMessage = $("#requestMessage").val();
                $.ajax({
                    url: "/request/made-request-book",
                    method: "post",
                    data: {'bookId': id, 'userId':userId, 'message': requestMessage},
                    success:function () {
                        alert("Запрос на чтение отправлен владельцу ");
                    }
                });
            }
        });
    }

    $("#btn-request-view").click(function () {
        userId = localStorage.getItem("userId");
            $.ajax({
                data: {
                    'userId': userId,
                },
                url: "/request/get-not-answered-request",
                method: "get",
                success: function (data) {
                    var result = data;
                    var optional = "<table>";
                    for (var i = 0; i != result.length; ++i) {
                        optional += "<tr>";
                        optional += "<td>"+result[i].book.title+"</td>";
                        optional += "<td>"+result[i].book.author+"</td>";
                        optional += "<td>"+result[i].message+"</td>";
                        var btn_name = "btn_book_answer" + result[i].id;
                        optional += "<td><button class='btn btn-info' id='" + btn_name+ "' type='button'>Ответ</button></td>";
                    }
                    optional+="</table>";
                    tbody_response = $("#tbody_response");
                    tbody_response.html(optional);


                    for (var i = 0; i != result.length; ++i) {
                        btn_name = "btn_book_answer" + result[i].id;
                        var btn_book_answer = $("#"+btn_name);
                        btn_book_answer.bind("click",{ param: result[i].id },openModalAnswerBook);
                    }

                }
            });

    });

    function openModalAnswerBook(event){
        var id = event.data.param;

        var c = $('<div class="box-modal" />');
        c.html($('.b-text').html());
        c.prepend('<div class="box-modal_close arcticmodal-close">X</div>');

        $('#responseBook').arcticmodal({
            closeOnEsc: false,
            closeOnOverlayClick: false,
            content: c,
            overlay: {
                css: {
                    backgroundColor: '#fff',
                    backgroundRepeat: 'repeat',
                    backgroundPosition: '50% 0',
                    opacity: .75
                }
            },
            afterClose: function(data, el) {
                answerMessage = $("#answerMessage").val();
                dateExpired = $("#dateExpired").val();
                enabled = $("#enabled").prop('checked');
                $.ajax({
                    url: "/request/made-response-book",
                    method: "post",
                    data: {'responseRequestId': id, 'answer':answerMessage, 'expired': dateExpired,'enabled': enabled},
                    success:function () {
                        alert("Вы дали ответ на запрос");
                    }
                });
            }
        });
    }

    $("#back").click(function () {
        if(currentPage > 0 && openBookId != "") {
            currentPage--;
            $.ajax({
                url: "/book/book-page",
                method: "post",
                data: {'bookId': openBookId, 'userId':userId,"index": currentPage},
                success:function () {
                    viewCurrentPage(currentPage);
                }
            });
        }
    });

    $("#forward").click(function () {
        if(currentPage < totalPage && openBookId != "") {
            currentPage++;
            $.ajax({
                url: "/book/book-page",
                method: "post",
                data: {'bookId': openBookId, 'userId':userId,"index": currentPage},
                success:function () {
                    viewCurrentPage(currentPage);
                }
            });
        }
    });

});

