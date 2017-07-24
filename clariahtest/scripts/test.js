function login() {
    /*
    Authorization URL:
    https://authz.proxy.clariah.nl/oauth/authorize?response_type=code&client_id=cool_app_id
    &scope=groups&state=example&redirect_uri=https://authz-playground.proxy.clariah.nl/redirect
    */

    var url = "https://authz.proxy.clariah.nl/oauth/authorize";
    var response_type = "code";
    var client_id = "Timbuctoo_test1";
    var redirect_uri = "http://localhost:3000/auth/example/callback";
    var query_params = "?response_type=" + response_type + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
    window.open(url + query_params);
}
