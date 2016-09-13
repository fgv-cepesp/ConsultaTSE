const elixir = require('laravel-elixir');
elixir.config.publicPath = 'src/main/webapp';
elixir.config.assetsPath = 'src/main/resources/web';

const packages = {
    jquery: 'node_modules/jquery/dist/',
    bootstrap: 'node_modules/bootstrap/dist/',
    icheck: 'node_modules/icheck/',
    tokenfield: 'node_modules/bootstrap-tokenfield/dist/',
    typeahead: 'node_modules/typeahead.js/dist/'
};

elixir(function (mix) {
    mix
        //JQuery
        //.copy(packages.jquery + 'jquery.min.js', elixir.config.publicPath + '/js/jquery.js')

        //Bootstrap
        //.copy(packages.bootstrap, elixir.config.publicPath + '/bootstrap')

        //iCheck
        //.copy(packages.icheck + 'skins/flat/blue.png', elixir.config.publicPath + '/icheck/blue.png')
        //.copy(packages.icheck + 'skins/flat/blue@2x.png', elixir.config.publicPath + '/icheck/blue@2x.png')
        //.copy(packages.icheck + 'skins/flat/blue.css', elixir.config.publicPath + '/icheck/icheck.css')
        //.copy(packages.icheck + 'icheck.min.js', elixir.config.publicPath + '/icheck/icheck.min.js')

        //Bootstrap Tokenfield
        //.copy(packages.tokenfield + 'bootstrap-tokenfield.min.js', elixir.config.publicPath + '/bootstrap-tokenfield/js/')
        //.copy(packages.tokenfield + 'css/', elixir.config.publicPath + '/bootstrap-tokenfield/css/')

        .copy(packages.typeahead, elixir.config.assetsPath + '/js/typeahead')
        .scripts('typeahead/*.js', elixir.config.publicPath + '/js/typeahead.js')

        .less('style.less', elixir.config.publicPath + '/css/style.css')

        .coffee([
            'framework/View.coffee',
            'framework/AsyncView.coffee',
            'framework/LoadingView.coffee',
            'framework/FadeViewAnimation.coffee',
            'framework/SlideViewAnimation.coffee',
        ], elixir.config.publicPath + '/js/framework.js')

        .coffee([
            'consulta/Jobs.coffee',
            'consulta/Query.coffee',
            'consulta/QueryView.coffee',
            'consulta/FilterView.coffee',
            'consulta/ColumnField.coffee',
            'consulta/Partido.coffee',
            'consulta/Candidato.coffee',
            'consulta/Regiao.coffee',
            'consulta/RequiredFieldsFilterView.coffee',
            'consulta/ElectionsFilterView.coffee',
            'consulta/ColumnsFilterView.coffee',
            'consulta/OptionalFiltersView.coffee',
        ], elixir.config.publicPath + '/js/consulta.js')

        .coffee('app.coffee');
});