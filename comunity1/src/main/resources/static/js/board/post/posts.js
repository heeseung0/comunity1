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
        }
    }
})

function postGet(thisURL) { //중복 변수,함수 제거 목적으로 하나로 통합
    const url = location.href.split('/')[3];
    const url2 = location.href.split('/')[4];
    const tbody = document.querySelector(".jsTarget_tbody");
    const postHeader = document.querySelector(".header");
    const postFooter = document.querySelector(".footer");
    const postContentHeader = document.querySelector(".header_title");
    const postContent = document.querySelector(".read_body");
    const postContentFooter = document.querySelector(".read_footer");
    const pagingFooter = document.querySelector(".board_footer_others");

    let titleName = "";
    switch(thisURL){
        case 'Notice':
            titleName = "공지사항";
            break;
        case 'Free':
            titleName = "자유게시판";
            break;
    }

    $.ajax({
        async: false,
        type: "get",
        url: "/api/board/" + thisURL + "/getPosts",
        success: (response) => {
            console.log(response);
            const maxPostsInPage = 20;
            const maxPage = Math.floor((response.data.length / maxPostsInPage)) + 1;
            const strCut = "page=";
            const nowPage = url.indexOf(strCut) > 0 ? url.substring(url.indexOf(strCut) + String(strCut).length) :
                url2 != undefined ? url2.indexOf(strCut) > 0 ? url2.substring(url2.indexOf(strCut) + String(strCut).length) : 1 : 1;
            const nowParam = url.indexOf("?") > 0 ? url.substring(url.indexOf("?")) :
                url2 != undefined ? url2.indexOf("?") > 0 ? url2.substring(url2.indexOf("?")) : "" : "";

            tbody.innerHTML = "";

            //----------페이징 처리 Footer----------
            const paging_tmp = nowPage > 10 ? Math.floor(((nowPage - 1) / 10)) * 10 + 1 : 1;
            let inner_tmp = "";
            inner_tmp = `
                    전체글 : ${response.data.length}개<br>
                    <div class="board_footer_others_inner">
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=1'>&lt;&lt;
                        </li>
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${paging_tmp - 1}'>&lt;
                        </li>
                `;
            for (let i = paging_tmp; i <= maxPage && i <= paging_tmp + 9; i++) {
                inner_tmp += `
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${i}'>${i} </a>
                        </li>
                    `;
            }
            inner_tmp += `
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${paging_tmp + 10}'>&gt;
                        </li>
                        <li class="paging_type1">
                            <a href='${location.pathname}?page=${maxPage}'>&gt;&gt;
                        </li>
                    </div>
                `;

            pagingFooter.innerHTML = inner_tmp;

            response.data.forEach((post, index) => {
                let type_class = "";
                let type_content = "";
                let date_process = post.date_post;

                //----------게시글 불러오는곳------------
                //----------전처리----------
                date_process = date_process.replace(/-/g, '.');
                date_process = date_process.substring(0, date_process.indexOf("T"));


                if(thisURL == 'Notice'){
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
                }else if(thisURL == 'Free'){
                    switch (post.type) {
                        case 1:
                            type_class = "category_free";
                            type_content = "자유";
                            break;
                    }
                }

                //----------후처리----------
                if (nowPage * maxPostsInPage - maxPostsInPage <= index && index < nowPage * maxPostsInPage) {
                    tbody.innerHTML += `
                        <tr>
                            <td class="no"><span>${post.id}</span></td>
                            <td class="${type_class}"><span>${type_content}</span></td>
                            <td class="title"><a href="/${thisURL}/${post.id}${nowParam}">${post.title}</a></td>
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
                    }
                }
            })
        }, error: (error) => {
            console.log("에러!!!");
            console.log(error);
        }
    });
}

function btnEvent_MD(boardURL, postID) {
    $(document).ready(function () {
        $('.postModify').click(function () {
            location.href += "/PostModify";
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