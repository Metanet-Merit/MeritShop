$scrap= {
    //itemId: document.getElementById('itemId').value,
    //userId:document.getElementById('userId').value,
    addScrap:function(){
        url = '/myPage/scrap';
        param = {
            itemId:1,
            userId:1
        }

        $ajax.post(url, param, $scrap.addScrapCallBack, $scrap.addScrapErrCallback);
    },
    getScraps:function (){
        url = '/myPage/scrap';
        param = {userId:1}

        $ajax.get(url, param, $scrap.getScrapsCallBack, $scrap.getScrapsErrCallback);
    },
    getScrapsCallBack:function (response){
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0) {
            var obj=JSON.parse(response).rtnObj;
            var msg = JSON.parse(response).rtnMsg;

            alert("찜정보 콘솔확인");
            console.log(obj);



        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);

        }


    },

    addScrapCallBack:function (response){
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0) {

            alert("찜하기가 완료되었습니다");

        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);

        }


    },
    addScrapErrCallback:function (response){
        console.log(response);//에러화면 띄우기
        alert("잠시 후 다시 시도해 주세요");
        return;
    },


}