$review = {

    //itemId:document.getElementById("itemId").value,
    //userId:$Cookie.getCookie(),

    getReviewPages: function (page) {
        var userId = $Cookie.getCookie();
        var url = '/myPage/reviewPages';
        var param = {
            userId: userId,
            page: page
        }

        $ajax.get(url, param, $review.getReviewPagesCallBack, $review.getReviewPagesErrCallback);

    },

    getReviewPagesErrCallback: function (response) {
        alert("잠시후 다시 시도해 주세요");
    },

    getReviewPagesCallBack: function (response) {
        var result = JSON.parse(response);
        if (result.rtnCd != 0) {
            if (result.rtnMsg != '') {
                alert(result.rtnMsg);
            } else {
                alert('데이터 조회를 실패했습니다.');
            }
            return;
        }
        var rtnObj = result.rtnObj;
        var endPage = Math.min(rtnObj.totalPages, rtnObj.pageable.pageNumber + 4)
        var startPage = Math.max(1, rtnObj.pageable.pageNumber - 4)
        var reviews = rtnObj.content;

        console.log('endPage : ' + endPage);
        console.log('startPage : ' + startPage);
        var innerHtml = '';

        var html =
            '<tr>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="area">\n' +
            '                        <a class="thum" href="#" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '<img style="width: 25px; height: 25px" src="{uuidName}" alt="아이템이미지">\n' +
            '                        </a>\n' +
            '                        <div class="textus">\n' +
            '                            <dl class="data review-data">\n' +
            '                                <dt>구매일자</dt>\n' +
            '                              <!--  <dd></dd>--><dt>{orderDate}</dt> </dl>\n' +
            '                            <a class="" href="javascript:mypage.gdasList.moveGoodsDetailReview(\'A000000149383\',\'리뷰_리뷰상품\');" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '                                <!--<span class="tit"></span>\n' +
            '                                <span class="txt oneline"></span>-->\n' +
            '\n' +
            '                               <!-- <p class="txt_option"><em></em></p>-->\n' +
            '<span class="tit">{category}</span>   <span class="txt oneline">{orderItemName}</span>  </a>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="textus" style="width:90%;">\n' +
            '                        <dl class="data review-data">\n' +
            '                            <dt>작성일자</dt>\n' +
            '                            <dd></dd><dd>{reviewDate}</dd> </dl>\n' +
            '                        <div class="rating">\n' +
            '\t\t\t\t\t\t\t<span class="txt"></span>\n' +
            '                            <span class="point pt5"></span>\n' +
            '                        </div>\n' +
            '                     </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td>\n' +
            '          <!--      <button type="button" class="btn-review--small"  data-write-yn="Y" onclick="mypage.gdasCompleteList.appraisalModify(this);">리뷰수정</button><br>\n' +
            '--><input type="hidden" value="{content}"><button type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$review.changeModalContent(this)">리뷰보기</button><br></td></tr>'

        reviews.forEach(review => {
            var uuidName = review.uuidName;
            var orderDate = review.orderDate;
            var reviewDate = review.reviewDate;
            var content = review.content;
            var category = review.category;
            var orderItemName = review.orderItemName;
            var rate = review.rate;

            innerHtml += html
                .replace('{uuidName}', uuidName)
                .replace('{orderDate}', orderDate)
                .replace('{reviewDate}', reviewDate)
                .replace('{content}', content)
                .replace('{category}', category)
                .replace('{orderItemName}', orderItemName)
        });
        document.getElementById('reviews').innerHTML = innerHtml;

    },

    getReviews: function () {
        userId = $Cookie.getCookie();
        url = '/myPage/reviews';
        param = {userId: userId}

        $ajax.get(url, param, $review.getReviewsCallBack, $review.getReviewsErrCallback);
    },
    getItemReviews: function () {
        url = '/myPage/itemReviews';
        param = {itemId: 1}
        //param={itemId:itemId}
        $ajax.get(url, param, $review.getItemReviewsCallBack, $review.getItemReviewsErrCallback);
    },
    getItemReviewsErrCallback: function (response) {
        console.log(response);//에러화면 띄우기
        alert("잠시 후 다시 시도해 주세요");
        return;
    },
    getItemReviewsCallBack: function (response) {

        var result = JSON.parse(response);

        var prefix = '\n' +
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
        const middle =
            ' <tbody>\n' +
            '        <tr>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="area">\n';
        const middle2 =
            '                        </a>\n' +
            '                        <div class="textus">\n' +
            '                            <dl class="data review-data">\n' +
            '                                <dt>구매일자</dt>\n' +
            '                              <!--  <dd></dd>-->'
        const html_categoryNitem = ' </dl>\n' +
            '                            <a class="" href="javascript:mypage.gdasList.moveGoodsDetailReview(\'A000000149383\',\'리뷰_리뷰상품\');" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '                                <!--<span class="tit"></span>\n' +
            '                                <span class="txt oneline"></span>-->\n' +
            '\n' +
            '                                <p class="txt_option"><em></em></p>\n';

        const html_review_date =
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
            '                            <dd></dd>';

        const btn =
            ' </dl>\n' +
            '                        <div class="rating">\n' +
            '\t\t\t\t\t\t\t<span class="txt"></span>\n' +
            '                            <span class="point pt5"></span>\n' +
            '                        </div>\n' +
            '                     </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td>\n' +
            '           <!--     <button type="button" class="btn-review--small"  data-write-yn="Y" onclick="mypage.gdasCompleteList.appraisalModify(this);">리뷰수정</button><br>\n-->';

        const suffix =

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
            var reviews = rtnObj['reviews'];

            reviews.forEach(function (review) {
                const orderItemName = review['orderItemName'];
                const review_content = review['content'];
                const rate = review['rate'];
                const review_date = review['reviewDate'];
                const order_date = review['orderDate'];
                const category = review['category'];
                const userName = review['userName'];
                const url = review['uuidName'];

                const button = '<input type="hidden" value="' + review_content + '"><button type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$review.changeModalContent(this)">리뷰보기</button><br></td></tr>'
                const img = '<img style="width: 15px; height: 15px" src=' + url + '" alt="[승관 Pick] 라네즈 네오 쿠션 매트 15g 5종 택1" >\n';

                content += middle + img + middle2 + '<dd>' + order_date + '</dd>' + html_categoryNitem + '<span class="tit">' + category + '</span>' +
                    '   <span class="txt oneline">' + orderItemName + '</span>' + html_review_date +
                    '<dd>' + review_date + '</dd>' + '<dd>' + userName + '</dd>' + btn + button;

            });

            document.getElementById('content').innerHTML = prefix + content + suffix;

        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);
            return;
        }
    },

    addReview: function () {

        alert("리뷰가 등록되었습니다");
    },

    changeModalContent: function (e) {

        $(".modal-body").text(e.previousSibling.value);

    },


    getReviewsCallBack: function (response) {

        var result = JSON.parse(response);

        var prefix = '\n' +
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
            '            <th scope="col" style="text-align: center">상품</th>\n' +
            '            <th scope="col" colspan="2" style="text-align: center">리뷰</th>\n' +
            '        </tr>\n' +
            '        </thead>';
        const middle =
            ' <tbody id="reviews" style="align-content: center">\n' +
            '        <tr>\n' +
            '            <td class="subject">\n' +
            '                <div class="area">\n' +
            '                    <div class="area">\n' +
            '                        <a class="thum" href="#" data-attr="리뷰^리뷰상품^상품클릭">\n';
        const middle2 =
            '                        </a>\n' +
            '                        <div class="textus">\n' +
            '                            <dl class="data review-data">\n' +
            '                                <dt>구매일자</dt>\n' +
            '                              <!--  <dd></dd>-->';
        const html_categoryNitem = ' </dl>\n' +
            '                            <a class="" href="javascript:mypage.gdasList.moveGoodsDetailReview(\'A000000149383\',\'리뷰_리뷰상품\');" data-attr="리뷰^리뷰상품^상품클릭">\n' +
            '                                <!--<span class="tit"></span>\n' +
            '                                <span class="txt oneline"></span>-->\n' +
            '\n' +
            '                               <!-- <p class="txt_option"><em></em></p>-->\n';

        const html_review_date =
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
            '                            <dd></dd>';

        const btn =
            ' </dl>\n' +
            '                        <div class="rating">\n' +
            '\t\t\t\t\t\t\t<span class="txt"></span>\n' +
            '                            <span class="point pt5"></span>\n' +
            '                        </div>\n' +
            '                     </div>\n' +
            '                </div>\n' +
            '            </td>\n' +
            '            <td>\n' +
            '          <!--      <button type="button" class="btn-review--small"  data-write-yn="Y" onclick="mypage.gdasCompleteList.appraisalModify(this);">리뷰수정</button><br>\n-->';

        const suffix =

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
            var reviews = rtnObj['reviews'];

            reviews.forEach(function (review) {
                const orderItemName = review['orderItemName'];
                const review_content = review['content'];
                const rate = review['rate'];
                const review_date = review['reviewDate'];
                const order_date = review['orderDate'];
                const category = review['category'];
                const url = review['uuidName'];
                const button = '<input type="hidden" value="' + review_content + '"><button type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$review.changeModalContent(this)">리뷰보기</button><br></td></tr>'
                const img =
                    '<img style="width: 25px; height: 25px" src=' + url + ' alt="아이템이미지" >\n';

                content += middle + img + middle2 + '<dt>' + order_date + '</dt>' + html_categoryNitem + '<span class="tit">' + category + '</span>' +
                    '   <span class="txt oneline">' + orderItemName + '</span>' + html_review_date +
                    '<dd>' + review_date + '</dd>' + btn + button;

            });


            document.getElementById('content').innerHTML = prefix + content + suffix;

        } else {
            var msg = JSON.parse(response).rtnMsg;
            alert(msg);
            return;
        }
    },
    getReviewsErrCallback: function (response) {
        console.log(response);//에러화면 띄우기
        alert("잠시 후 다시 시도해 주세요");
        return;
    },


}