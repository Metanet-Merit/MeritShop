$review= {

    getReviews:function(){
        url = '/myPage/reviews';
        param = {userId:1}

        $ajax.get(url, param, $review.getReviewsCallBack, $review.getReviewsErrCallback);
    },
    addReview: function () {

        alert("리뷰가 등록되었습니다");
    },

    getReviewsCallBack: function (response) {
        var rtnCd = JSON.parse(response).rtnCd;

        if (rtnCd == 0) {
/*
            var reviews = JSON.parse(response).rtnObj;
            var content = '';

            chatRooms.forEach(function (chatRoom) {
                var chatRoomId = chatRoom['id'];
                var title = chatRoom['title'];
                var room = '<a href="javascript:;"onclick="$chatRoom.enterChatRoom(' + chatRoomId + ')"> ' + title + '</a></br>';

                content += room;

            });
            document.getElementById('chatRoomId').innerHTML = '</br>' + content;

            return;*/
            var content= '<table border ="1" width="50%" height=200 bgcolor=#11a9c1 bordercolor="white" cellspacing="5">\
                <tr align="center" bgcolor="white">\
                <td rowspan="2">rawspan 사용</td>\
            <td>김씨</td>\
            <td>이씨</td>\
            <td>박씨</td>\
        </tr>\
            <tr align="center" bgcolor="white">\
                <td>100원</td>\
                <td>200원</td>\
                <td>400원</td>\
            </tr>\
            <tr align="center" bgcolor="white">\
                <td colspan="4">\
                    colspan 사용\
                </td>\
            </tr>\
        </table>';

            document.getElementById('content').innerHTML = content;
        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);
            return;
        }
    },
    getReviewsErrCallback: function (response) {

    },

    getChatRoomsErrCallback: function (response) {
        console.log(response);//에러화면 띄우기
        alert("잠시 후 다시 시도해 주세요");
        return;
    }
}