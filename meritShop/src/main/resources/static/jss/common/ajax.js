$ajax = {
    ajax: function (url, param, type, callback, errCallback) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () { // 요청에 대한 콜백함수
            if (xhr.readyState === xhr.DONE) { // 요청이 완료되면 실행
                if (xhr.status === 200 || xhr.status === 201) { // 응답 코드가 200 혹은 201 api호출 성공
                    callback(xhr.responseText);
                } else {
                    errCallback(xhr.responseText);
                }
            }
        };
        xhr.open(type, url); // http 메서드와 URL설정
        if ('GET' == type) {
            xhr.send(); // 요청 전송
        } else {
            xhr.setRequestHeader('Content-Type', 'application/json'); // 콘텐츠 타입을 json으로
            xhr.send(JSON.stringify(param)); // 자바스크립트데이터를 JSON 문자열로  전송한다
        }
    },

    get: function (url, param, callback, errCallback) {
        for (key in param) {
            var value = param[key];
            if (url.includes('?')) {
                url += '&';
            } else {
                url += '?';
            }
            url += key + '=' + value;
        }
        $ajax.ajax(url, {}, 'GET', callback, errCallback);
    },

    post: function (url, param, callback, errCallback) {
        $ajax.ajax(url, param, 'POST', callback, errCallback);
    }


}
