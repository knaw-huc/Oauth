function login() {
    /*
    Authorization URL example from Playground:
    https://authz.proxy.clariah.nl/oauth/authorize?response_type=code&client_id=cool_app_id
    &scope=groups&state=example&redirect_uri=https://authz-playground.proxy.clariah.nl/redirect
    */
    //var url = "https://authz.proxy.clariah.nl/oauth/authorize";
    var url = "http://localhost:8080/oauth2/authorize";
    var response_type = "code";
    var client_id = "cool_app_id"; //for the OAuth APIs server library
    //var client_id = "Timbuctoo_test1"
    //var redirect_uri = "http://localhost:8084/auth/example/callback";
    var redirect_uri = "http://localhost:8084/redirect/";
    var query_params = "?response_type=" + response_type + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
    window.open(url + query_params);
}
