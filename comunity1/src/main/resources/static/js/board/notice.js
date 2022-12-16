window.addEventListener('load', () => {
    const tbody = document.querySelector(".jsTarget_tbody");
    const postHeader = document.querySelector(".footer");
    const postContent = document.querySelector(".read_body");

    if (tbody != null) {
        $.ajax({
            async: false,
            type: "get",
            url: "/api/board/notice/getPost",
            success: (response) => {
                console.log(response);

                tbody.innerHTML = "";
                response.data.forEach(post => {
                    let type_class = "";
                    let type_content = "";
                    let date_process = post.date_post;

                    //----------전처리----------
                    date_process = date_process.replace(/-/g, '.');
                    date_process = date_process.substring(0, date_process.indexOf("T"));

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

                    //----------후처리----------
                    tbody.innerHTML += `
                        <tr>
                            <td class="no"><span>${post.id}</span></td>
                            <td class="${type_class}"><span>${type_content}</span></td>
                            <td class="title"><a href="/Notice/${post.id}">${post.title}</a></td>
                            <td class="author"><span>${post.writer}</span></td>
                            <td class="time"><span>${date_process}</span></td>
                            <td class="readNum"><span>${post.view}</span></td>
                        </tr>
                    `;

                    //----------게시글 내부일 경우에----------
                    if (postHeader != null && postContent != null) {
                        if (post.id == location.pathname.substring(8)) {
                            let postTime = post.date_post.substring(post.date_post.indexOf("T")+1);

                            postHeader.innerHTML = `
                                <a class="link" href="/Notice/${post.id}">127.0.0.1:8000/Notice/${post.id}</a>
                                <p class="sum">
                                    <span class="read" style="margin-right:40px"><b>조회 수</b> <span class="num">${post.view}</span></span>
                                    <span class="time"><b>등록일</b> <span class="num">${date_process}<span style="margin-right:20px"></span> ${postTime}</span></span>
                                </p>
                            `;

                            postContent.innerHTML = post.contents;
                        }
                    }
                })
            }, error: (error) => {
                console.log("에러!!!");
                console.log(error);
            }
        });
    }


})