$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (user, status) {
            $('.myForm #idFormEdit').val(user.id);
            $('.myForm #nameFormEdit').val(user.username);
            $('.myForm #ageFormEdit').val(user.age);
            $('.myForm #firstNameFormEdit').val(user.first_name);
            $('.myForm #emailFormEdit').val(user.email);
        });
        $('.myForm #exampleModal').modal();

    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});