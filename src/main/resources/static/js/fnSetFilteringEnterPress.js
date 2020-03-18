jQuery.fn.dataTableExt.oApi.fnSetFilteringEnterPress = function (oSettings) {
    var press = this;

    this.each(function (i) {
        $.fn.dataTableExt.iApiIndex = i;
		const input = $('input', press.fnSettings().aanFeatures.f);
		input.unbind('keyup search input').bind(
            'keyup search input',
            function (e) {
                if (input.val().length == "" || input.val().length > 2) {
                    press.fnFilter(input.val());
                }
            });
        return this;
    });
    return this;
};