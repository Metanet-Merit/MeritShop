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
    modifyReview: function (orderItemId) {

        location.href = '/reviewForm/' + orderItemId;

    },

    deleteReview: function (reviewId) {
        var url = '/myPage/deleteReview';
        var param = {
            reviewId: reviewId
        }

        $ajax.get(url, param, $review.deleteReviewCallBack, $review.deleteReviewErrCallback);
    },
    deleteReviewCallBack: function (response) {
        var result = JSON.parse(response);
        if (result.rtnCd != 0) {
            alert(result.rtnMsg);

        } else {
            (function (x) {
                $review.getReviews();
            })();
        }

    },
    deleteReviewErrCallback: function () {
        alert("잠시후 다시 시도해 주세요");
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
            '--><input type="hidden" value="{content}"><button type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$review.changeModalContent(this)">리뷰보기</button><br></td></tr>' +
            '   <nav aria-label="Page navigation example">\n' +
            '                            <ul class="pagination justify-content-center">\n' +
            '                                <li class="page-item" classappend="{disabled}" >\n' +
            '                                    <a class="page-link" onclick="{url1}">Pre</a>\n' +
            '                                </li>"{numbers}"' +
            '                                <li class="page-item" th:classappend="${listPage.totalPages==listPage.pageable.pageNumber+1}?\'disabled\'">\n' +
            '                                    <a class="page-link" href="#"  th:href="@{/admin/qnas(page=${listPage.pageable.pageNumber+1})}">Next</a>\n' +
            '                                </li>\n' +
            '                            </ul>\n' +
            '                        </nav>'
        var i = 0;
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
                .replace('{url1}', "$review.getreviewPages(page)");


        });
        document.getElementById('reviews').innerHTML = innerHtml;

    },

    getReviews: function (page) {
        userId = $Cookie.getCookie();
        url = '/myPage/reviews';
        param = {
            userId: userId,
            page: page
        }

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

    addReview: function () {

        alert("리뷰가 등록되었습니다");
    },

    changeModalContent: function (e) {
        $("#modal-body").text(e.previousSibling.value);


        $('#imgDiv').hide();
        var uuidInput = $(e).prev().prev();
        if (!uuidInput || uuidInput.length < 1) {
            return;
        }
        uuidInput = uuidInput[0];
        if (!uuidInput || uuidInput.value == '' || uuidInput.value == 'null') {
            return;
        }
        var review_uuidName = '/review/' + uuidInput.value;
        $('#imgDiv>img')[0].src = review_uuidName;
        $('#imgDiv').show();
    },


    getReviewsCallBack: function (response) {

        var result = JSON.parse(response);
        var rtnObj = result.rtnObj;
        var reviews = rtnObj.content;

        var endPage = Math.min(rtnObj.totalPages, rtnObj.pageable.pageNumber + 4)
        var startPage = Math.max(1, rtnObj.pageable.pageNumber - 4)


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
            ' <tbody id="reviews" style="text-align: center">\n' +
            '        <tr>\n' +
            '            <td class="subject"style="text-align: center">\n' +
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
            '                        <dl class="data review-data" style="text-align: center">\n' +
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
            '    </table>\n';


        if (result.rtnCd == 0) {


            var content = '';


            reviews.forEach(function (review) {
                const orderItemName = review.orderItemName;
                const orderItemId = review.orderItemId;
                const review_content = review.content;
                const rate = review.rate;
                var review_date = review.reviewDate;
                var order_date = review.orderDate;
                const category = review.category;
                const url = review.uuidName;
                const reviewId = review.reviewId;
                const review_uuidName = review.review_uuidName;
                const button = '<input type="hidden" value="' + review_uuidName + '"> <input type="hidden" value="' + review_content + '"><button id="btn' + reviewId + '" type="button" class="btn-review--small" data-toggle="modal" data-target="#myModal" onclick="$review.changeModalContent(this)">리뷰보기</button><br>'
                const deleteButton = '<input type="hidden" value="' + review_content + '"><button type="button" class="btn-review--small" onclick="$review.deleteReview(' + reviewId + ')">리뷰삭제</button><br></td></tr>'
                const modifyButton = '<input type="hidden" value="' + review_content + '"><button type="button" class="btn-review--small" onclick="$review.modifyReview(' + orderItemId + ')">리뷰수정</button><br>'
                const img =
                    '<img style="width: 25px; height: 25px" src=' + url + ' alt="아이템이미지" >\n';
                order_date = order_date.substr(0, 10);
                review_date = review_date.substr(0, 10);
                content += middle + img + middle2 + '<dt>' + order_date + '</dt>' + html_categoryNitem + '<span class="tit">' + category + '</span>' +
                    '   <span class="txt oneline">' + orderItemName + '</span>' + html_review_date +
                    '<dd>' + review_date + '</dd>' + btn + button + deleteButton;

            });

            var numbers =
                '<nav aria-label="Page navigation example">\n' +
                '<ul class="pagination justify-content-center">';

            if (1 == rtnObj.pageable.pageNumber + 1) {
                numbers += '<li class="page-item disabled">';
            } else {
                numbers += '<li class="page-item">';
            }
            numbers += '<a class="page-link" onclick="$review.getReviews(' + (rtnObj.pageable.pageNumber - 1) + ')">Pre</a></li>';

            for (var i = startPage; i <= endPage; i++) {

                if (i == rtnObj.pageable.pageNumber + 1) {
                    numbers += '<li class="page-item disabled">'
                } else {
                    numbers += '<li class="page-item">'
                }

                numbers +=
                    '<a class="page-link" onclick="$review.getReviews(' + (i - 1) + ')">' + i + '</a></li>';
            }

            if (rtnObj.totalPages == rtnObj.pageable.pageNumber + 1) {
                numbers += '<li class="page-item disabled">\n';
            } else {
                numbers += '<li class="page-item">\n';
            }

            numbers += '  <a class="page-link" onclick="$review.getReviews(' + rtnObj.pageable.pageNumber + 1 + ')" >Next</a>\n' +
                '   </li>\n' +
                '   </ul>\n' +
                '   </nav>' +
                '\n' +
                '\n' +
                '\n' +
                '    <!-- 공통 딤처리 -->\n' +
                '    <div class="dim"></div>\n' +
                '    <!-- //공통 딤처리 -->\n' +
                '\n' +
                '</div>\n' +
                '</body>';


            document.getElementById('content').innerHTML = prefix + content + suffix + numbers;

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