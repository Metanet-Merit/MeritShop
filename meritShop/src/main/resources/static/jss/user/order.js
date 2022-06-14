$order= {
    //html에서 유저아이디 가져오기 or 쿠키에서 가져오기 or 컨트롤러에서 가져오기
    getOrders:function(){
        userId=$Cookie.getCookie();
        url = '/myPage/orderItems';
        param = {userId:userId}

        $ajax.get(url, param, $order.getOrderListCallBack, $order.getOrderListErrCallback);
    },

    changeModalContent:function(e){

        if(e.previousSibling.value=="true"){
            alert("이미 리뷰를 작성했네요!");
        }
        else{

            $(".modal-body").text("리뷰쓰기 페이지로 이동하기");
        }


    },

    getOrderListCallBack: function (response) {

        var result = JSON.parse(response);

        var prefix= '\n' +
            '<body>\n' +
            '<div style="text-align: center" class="mypage-conts">\n' +
            '    <div class="title-area linezero">\n' +
            '    </div>\n' +
            '    <table class="board-list-2s mgT20 new">\n' +
            '        <colgroup>\n' +
            '            <col style="width:49%;">\n' +
            '            <col style="width:39%;">\n' +
            '            <col style="width:12%;">\n' +
            '        </colgroup>\n' +
            '        <thead>\n' +
            '        <tr>\n' +
            '            <th scope="col">상품</th>\n' +
            '            <th scope="col" colspan="2">리뷰</th>\n' +
            '        </tr>\n' +
            '        </thead>';
        const middle=
            ' <tbody>\n' +
            '        <tr>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="area">\n' +
            '                        <a class="thum" href="#" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '                            <img style="width: 15px; height: 15px" src="https://image.oliveyoung.co.kr/uploads/images/goods/10/0000/0014/A00000014938397ko.jpg?l=ko" alt="[승관 Pick] 라네즈 네오 쿠션 매트 15g 5종 택1" onerror="common.errorImg(this);">\n' +
            '                        </a>\n' +
            '                        <div class="textus">\n' +
            '                            <dl class="data review-data">\n' +
            '                                <dt>구매일자</dt>\n' +
            '                              <!--  <dd></dd>-->'
        const html_categoryNitem=' </dl>\n' +
            '                            <a class="" href="javascript:mypage.gdasList.moveGoodsDetailReview(\'A000000149383\',\'리뷰_리뷰상품\');" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '                                <!--<span class="tit"></span>\n' +
            '                                <span class="txt oneline"></span>-->\n' +
            '\n' +
            '                                <p class="txt_option"><em></em></p>\n';

        const html_review_date=
            '  </a>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="textus" style="width:90%;">\n' +
            '                        <dl class="data review-data">\n' +
            '                            <dt>작성일자</dt>\n' +
            '                            <dd></dd>' ;

        const btn=
            ' </dl>\n' +
            '                        <div class="rating">\n' +
            '\t\t\t\t\t\t\t<span class="txt"></span>\n' +
            '                            <span class="point pt5"></span>\n' +
            '                        </div>\n' +
            '                     </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td>\n' +
            '                <!--<button type="button" class="btn-review--small"  data-write-yn="Y" onclick="mypage.gdasCompleteList.appraisalModify(this);">리뷰수정</button><br>\n-->' ;

        const suffix=

            '</tbody>\n' +
            '    </table>\n' +
            '\n' +
            '\n' +
            '\n' +
            '    <!-- 공통 딤처리 -->\n' +
            '    <div class="dim"></div>\n' +
            '    <!-- //공통 딤처리 -->\n' +
            '\n' +
            '</div>\n' +
            '</body>';


        if (result['rtnCd'] == 0) {

            var content = '';
            var rtnObj = result['rtnObj'];
            var orderItems = rtnObj['orderItems'];

            //String orderItemName;
          //  Integer count;
           // boolean reviewed;
            orderItems.forEach(function (orderItem) {
                const orderItemName = orderItem['orderItemName'];
                const count=orderItem['count'];
                const reviewed=orderItem['reviewed'];
                const orderDate=orderItem['orderDate'];

                const button='<input type="hidden" value='+reviewed+'><button type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$order.changeModalContent(this)">리뷰쓰러가기</button><br></td></tr>'

                content +=  middle+orderDate+'<dd>수량:'+count+'</dd>'+html_categoryNitem+'<span class="tit">'+'</span>' +
                    '   <span class="txt oneline">'+orderItemName+'</span>' +
                    '<dd>'+'</dd>'+btn+button;

            });


            document.getElementById('content').innerHTML = prefix + content+suffix;

        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);
            return;
        }
    },

    getOrderListErrCallback: function (response) {
        console.log(response);//에러화면 띄우기
        alert("잠시 후 다시 시도해 주세요");
        return;
    }
}