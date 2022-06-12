(function($) {
    $(function() {
        $('nav ul li a:not(:only-child)').click(function (e) {
            $(this).siblings('.nalumes-nucludem').toggle();
            $('.nalumes-nucludem').not($(this).siblings()).hide();
            e.stopPropagation();
        });

        $('html').click(function () {
            $('.nalumes-nucludem').hide();
        });
    })
})