$scrap = {
    deleteScrap:function (){
        var scrapId=document.getElementById("scrapId").value;
        userId = $Cookie.getCookie();
        url = '/myPage/scrap/delete/'+scrapId;
        param = {
            scrapId: scrapId
        }

        $ajax.post(url, param, $scrap.deleteScrapCallback, $scrap.deleteScrapErrCallback);
    },
    deleteScrapCallback:function (response){
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0){
            location.href = "/myPage/myScraps";
            return;
        }

        var errMsg=JSON.parse(response).rtnMsg;
        alert(errMsg);
        location.href = "/myPage/myScraps";
        return;

    },
    deleteScrapErrCallback:function(response){
        var errMsg=JSON.parse(response).rtnMsg;
        alert(errMsg);
        location.href = "/myPage/myScraps";
    },
    addScrap: function () {
        if(typeof userId == "undefined") {
            swal('찜 실패', "로그인 후 이용해주세요",'error')
                .then(function(){
                    location.href='/user/login';
                })
        }
        var itemId_=document.getElementById("itemId").value;
        var itemOptionId = document.getElementById("itemOptionId").value;
        userId = $Cookie.getCookie();
        url = '/myPage/scrap';
        param = {
            itemId: itemId_,
            itemOptionId: itemOptionId,
            userId: userId
        }

        $ajax.post(url, param, $scrap.addScrapCallBack, $scrap.addScrapErrCallback);
    },
    getScraps: function () {
        userId = $Cookie.getCookie();
        url = '/myPage/scrap';
        param = {userId: userId}

        $ajax.get(url, param, $scrap.getScrapsCallBack, $scrap.getScrapsErrCallback);
    },
    getScrapsCallBack: function (response) {
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0) {
            var obj = JSON.parse(response).rtnObj;
            var msg = JSON.parse(response).rtnMsg;

            alert("찜정보 콘솔확인");
            console.log(obj);

        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);

        }

    },

    addScrapCallBack: function (response) {
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0) {
            swal('찜 성공', "찜하기가 완료되었습니다.",'success');

        } else {
            var msg = JSON.parse(response).rtnMsg;
            swal('찜 실패', msg, 'error');
        }

    },
    addScrapErrCallback: function (response) {
        console.log(response);//에러화면 띄우기
        swal('찜하기 실패', "옵션을 선택 해주세요!",'error');
        return;
    },


}