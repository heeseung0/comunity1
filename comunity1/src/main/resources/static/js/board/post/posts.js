window.addEventListener('load', () => {
    const tbody = document.querySelector(".jsTarget_tbody");


    if (tbody != null) {
        let thisLocation = location.href.split('/')[3];

        if (thisLocation.indexOf("?") > 0) {
            thisLocation = thisLocation.substring(0, thisLocation.indexOf("?"));
        }

        switch (thisLocation) {
            case 'notice':
            case 'Notice':
                postGet('Notice');
                break;
            case 'free':
            case 'Free':
                postGet('Free');
                break;
            case 'qna':
            case 'QNA':
                postGet('QNA');
                break;
        }
    }
})

function postGet(thisURL) { //중복 변수,함수 제거 목적으로 하나로 통합
    let titleName = "";
    switch (thisURL) {
        case 'Notice':
            titleName = "공지사항";
            break;
        case 'Free':
            titleName = "자유게시판";
            break;
        case 'QNA':
            titleName = "질문게시판";
            break;
    }

    const searchParams = new URLSearchParams(location.search);
    let isSearch = false;
    let searchAjaxParams = {
        type: '',
        searchType: '',
        searchLike: ''
    }

    for (const param of searchParams) {
        switch(param[0]){
            case 'type':
                searchAjaxParams['type'] = param[1];
            case 'searchType':
                searchAjaxParams['searchType'] = param[1];
            case 'searchLike':
                searchAjaxParams['searchLike'] = param[1];

                isSearch = true;
                break;
            default:
                isSearch = false;
        }
    }

    if(isSearch){
        $.ajax({
            async: false,
            type: "get",
            url: "/api/board/" + thisURL + "/getSearchPosts",
            data: {
                type: searchAjaxParams['type'],
                searchType: searchAjaxParams['searchType'],
                searchLike: searchAjaxParams['searchLike']
            }, success: (response) => {
                console.log(response);
                paint(response, thisURL, titleName);
            }, error: (error) => {
                console.log(error);
            }
        });
    }else {
        $.ajax({
            async: false,
            type: "get",
            url: "/api/board/" + thisURL + "/getPosts",
            success: (response) => {
                console.log(response);
                paint(response, thisURL, titleName);
            }, error: (error) => {
                console.log("에러!!!");
                console.log(error);
            }
        });
    }
}

function paint(response, thisURL, titleName){
    const url = location.href.split('/')[3];
    const tbody = document.querySelector(".jsTarget_tbody");
    const postHeader = document.querySelector(".header");
    const postFooter = document.querySelector(".footer");
    const postContentHeader = document.querySelector(".header_title");
    const postContent = document.querySelector(".read_body");
    const postContentFooter = document.querySelector(".read_footer");
    const pagingFooter = document.querySelector(".board_footer_others");
    const maxPostsInPage = 20;
    const maxPageInPage = 10;
    const maxPage = (response.data.length % maxPostsInPage) >= 1 ?
        Math.floor((response.data.length / maxPostsInPage))+1 : Math.floor((response.data.length / maxPostsInPage)) ;
    const searchParams = new URLSearchParams(location.search);
    let nowPage = "1";
    let nowParam = "";
    const searchType = {
        Notice: ['알림','업뎃','중요'],
        Free: ['자유'],
        QNA: ['질문']
    };

    if(searchParams != null) {
        for (const param of searchParams) {
            if (param[0] == 'page') {
                nowPage = param[1];
            }else{
                nowParam += "&" + param[0] + "=" + param[1];
            }
        }
    }


    console.log(nowParam);
    tbody.innerHTML = "";

    //--------- Footer ----------
    //----------검색----------
    let inner_tmp = `전체글 : ${response.data.length}개<br>`;

    for(let i = 0; i < searchType[thisURL].length; i++) {
        if(i==0){
            inner_tmp += `
                <div class="board_footer_others_search">
                <form method="get">
                    <select name="type" class="search-type">
            `;
        }

        inner_tmp += "<option value=" + (i+1) +">" + searchType[thisURL][i] + "</option>";

        if(i==(searchType[thisURL].length)-1){
            inner_tmp += `
                    </select>
                    <select name="searchType" class="search-type2">
                        <option value="0">제목</option>
                        <option value="1">작성자</option>
                        <option value="2">내용</option>
                    </select>
                    <input name="searchLike" type="search" class="search-input" placeholder="검색">
                    <button type="submit" class="search-button">검색</button>
                </form>    
                </div>
            `;
        }
    }
    //----------페이징 처리----------
    const paging_tmp = nowPage > 10 ? Math.floor(((nowPage - 1) / 10)) * 10 + 1 : 1;

    for (let i = paging_tmp; i <= maxPage && i < paging_tmp + maxPageInPage; i++) {
        if(i==paging_tmp){
            if(paging_tmp - 1 < 1) {
                inner_tmp += `
                    <div class="board_footer_others_inner">
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=1${nowParam}'>&lt;&lt;</a>
                        </li>
                `;
            }else{
                inner_tmp += `
                    <div class="board_footer_others_inner">
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=1${nowParam}'>&lt;&lt;</a>
                        </li>
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${paging_tmp - 1}${nowParam}'>&lt;</a>
                        </li>
                `;
            }
        }
        inner_tmp += `
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${i}${nowParam}'>${i} </a>
                        </li>
        `;
        if(i == (paging_tmp + maxPageInPage)-1 || i == maxPage){
            if(paging_tmp + 10 <= maxPage){
                inner_tmp += `
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${paging_tmp + 10}${nowParam}'>&gt;</a>
                        </li>
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${maxPage}${nowParam}'>&gt;&gt;</a>
                        </li>
                    </div>
                `;
            }else{
                inner_tmp += `
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${maxPage}${nowParam}'>&gt;&gt;</a>
                        </li>
                    </div>
                `;
            }

            pagingFooter.innerHTML = inner_tmp;
        }
    }

    response.data.forEach((post, index) => {
        let type_class = "";
        let type_content = "";
        let date_process = post.date_post;

        //----------게시글 불러오는곳------------
        //----------전처리----------
        date_process = date_process.replace(/-/g, '.');
        date_process = date_process.substring(0, date_process.indexOf("T"));


        if (thisURL == 'Notice') {
            switch (post.type) {
                case 1:
                    type_class = "category_notice";
                    type_content = "알림";
                    break;
                case 2:
                    type_class = "category_update";
                    type_content = "업뎃";
                    break;
                case 3:
                    type_class = "category_imple";
                    type_content = "중요";
                    break;
            }
        } else if (thisURL == 'Free') {
            switch (post.type) {
                case 1:
                    type_class = "category_free";
                    type_content = "자유";
                    break;
            }
        } else if(thisURL == 'QNA') {
            switch (post.type) {
                case 1:
                    type_class = "category_qna";
                    type_content = "질문";
                    break;
            }
        }

        //----------후처리----------
        if (nowPage * maxPostsInPage - maxPostsInPage <= index && index < nowPage * maxPostsInPage) {
            tbody.innerHTML += `
                        <tr>
                            <td class="no"><span>${post.id}</span></td>
                            <td class="${type_class}"><span>${type_content}</span></td>
                            <td class="title"><a href="/${thisURL}/${post.id}?page=${nowPage}&${nowParam}">[${post.replyCount}] ${post.title}</a></td>
                            <td class="author"><span>${post.writer}</span></td>
                            <td class="time"><span>${date_process}</span></td>
                            <td class="readNum"><span>${post.view}</span></td>
                        </tr>
            `;
        }

        //----------게시글 내부일 경우에 내용 불러오는곳----------
        if (postFooter != null && postContent != null) {
            const postNumber = location.href.split('/')[4].split("?")[0];

            if (post.id == postNumber) {
                let boardURL = "/" + url;
                let postTime = post.date_post.substring(post.date_post.indexOf("T") + 1);

                postContentHeader.innerHTML = `<a href=${boardURL}>${titleName}</a>`;
                postHeader.innerHTML = `
                                <h1 class="title"><span>${post.title}</span></h1>
                                <div class="nick_area">${post.writer}</div>
                        `;
                postFooter.innerHTML = `
                                <a class="link" href="/${thisURL}/${post.id}">127.0.0.1/${thisURL}/${post.id}</a>
                                <p class="sum">
                                    <span class="read" style="margin-right:40px"><b>조회 수</b> <span class="num">${post.view}</span></span>
                                    <span class="time"><b>등록일</b> <span class="num">${date_process}<span style="margin-right:20px"></span> ${postTime}</span></span>
                                </p>
                        `;
                postContent.innerHTML = post.contents;

                if (sessionStorage.getItem('getLogin') == post.writer || sessionStorage.getItem('getLoginRole') == 'admin') {
                    postContentFooter.setAttribute('class', 'read_footer');

                    btnEvent_MD(boardURL, post.id);
                }

                //----------댓글----------
                if (sessionStorage.getItem('getLoginRole') != 'anonymousUser') {
                    const reply = document.querySelector(".feedback");
                    const reply_contents = document.querySelector(".reply_contents");
                    const reply_submit = document.querySelector(".comment-submit");

                    //댓글 등록
                    reply.setAttribute('class', 'feedback');
                    reply_submit.onclick = () => {
                        const boardPostReplyReqDto = {
                            writer: sessionStorage.getItem('getLogin'),
                            board: thisURL,
                            postnum: postNumber,
                            contents: reply_contents.value
                        }

                        $.ajax({
                            async: false,
                            type: "post",
                            url: "/api/board/postReply",
                            contentType: "application/json",
                            data: JSON.stringify(boardPostReplyReqDto),
                            dataType: "json",
                            success: () => {
                                location.reload();
                            }, error: (error) => {
                                console.log(error);
                            }
                        });
                    }
                }
                //댓글 읽어오기
                $.ajax({
                    async: false,
                    type: "get",
                    url: "/api/board/getReply/" + thisURL + "/" + postNumber,
                    success: (response) => {
                        console.log(response);
                        const comments = document.querySelector(".jsTarget_feedback");

                        response.data.forEach(data => {
                            const date = replaceDate(data.date_post);

                            comments.innerHTML += `
                                        <li>
                                            <div class="feedbackList_div">
                                                <b>${data.writer}</b>
                                                <span>${data.contents}</span>
                                                <span>${date[0]}<br>${date[1]}</span>
                                            </div>
                                        </li>
                                    `;
                        });
                    }, error: (error) => {
                        console.log(error);
                    }
                });
            }
        }
    })

    if(response.data.length == 0) {
        let boardURL = "/" + url;
        postContentHeader.innerHTML = `<a href=${boardURL}>${titleName}</a>`;
        $('.board_body_table, .board_read').css('display','none');
        pagingFooter.innerHTML = "검색 결과가 없습니다.";
    }

    for (const param of searchParams) {
        if(param[0] == 'type')
            $(".search-type").val(param[1]);
        if(param[0] == 'searchType')
            $(".search-type2").val(param[1]);
        if(param[0] == 'searchLike')
            $(".search-input").val(param[1]);
    }
}

function replaceDate(rawText) {
    let date = [];

    date[0] = rawText.replace(/-/g, '.');
    date[0] = date[0].substring(0, date[0].indexOf("T"));
    date[1] = rawText.substring(rawText.indexOf("T") + 1);

    return date;
}

function btnEvent_MD(boardURL, postID) {
    $(document).ready(function () {
        $('.postModify').click(function () {
            let replaceUrl = [];
            replaceUrl[0] = location.href.substring(0, location.href.indexOf("?"));
            replaceUrl[1] = location.href.substring(location.href.indexOf("?")+1);
            if(replaceUrl[0] == ''){
                location.href += "/PostModify";
            }else{
                location.href = replaceUrl[0] + "/PostModify?" + replaceUrl[1];
            }

        });

        $('.postDelete').click(function () {
            if (confirm("글을 삭제하시겠습니까?")) {
                $.ajax({
                    async: false,
                    type: 'delete',
                    url: boardURL + "/" + postID + "/PostDelete",
                    success: (response) => {
                        alert("성공적으로 삭제하였습니다.");
                        location.href = boardURL;
                    }, error: (error) => {
                        alert("삭제중 오류가 발생하였습니다.");
                    }
                })
            }
        });
    });
}