$Cookie={
    getCookie:function(){
        var result = null;
        var cookie = document.cookie.split(';');
        cookie.some(function (item) {
            //공백을제거
            key="userId";
            item = item.replace(' ','');

            var dic = item.split('=');

            if (key === dic[0]) {
                result = dic[1];
                return true;//break;
            }
        });
        return result;
    },
}