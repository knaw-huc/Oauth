function login() {
    var url = "http://localhost:8080/oauth2/authorize";
    var response_type = "code";
    var client_id = "cool_app_id"; //for the OAuth APIs server library
    var redirect_uri = "http://localhost:8084/redirect/";
    var query_params = "?response_type=" + response_type + "&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
    window.open(url + query_params);
}
