window.addEventListener("load", () => {
    init();

    const dropdown_Nav = document.querySelectorAll(".dropdown");
    const dropdown_Menu_Nav = document.querySelectorAll(".dropdown-menu");
    const postList_Nav = document.querySelectorAll(".postList");
    const tab_pane = document.querySelectorAll(".tab-pane");

    const widgetNotice = document.querySelector(".widgetNotice");
    const widgetEvent = document.querySelector(".widgetEvent");
    const widgetFAQ = document.querySelector(".widgetFAQ");
    const widgetFree = document.querySelector(".widgetFree");
    const widgetFile = document.querySelector(".widgetFile");
    const widgetPoint = document.querySelector(".widgetPoint");

    dropdown_Nav.forEach((list, index) => {
        list.addEventListener("mouseover", () => {
            dropdown_Menu_Nav.forEach((menu) => {
                menu.setAttribute("class", "dropdown-menu hidden");
            });
            dropdown_Menu_Nav[index].setAttribute("class", "dropdown-menu open");
        });
    });

    postList_Nav.forEach((list, index) => {
        list.addEventListener("mouseover", () => {
            //이하 클래스 초기화
            postList_Nav.forEach(inner_list => {
                inner_list.setAttribute("class", "postList");
            });

            tab_pane.forEach(pane => {
                pane.setAttribute("class", "tab-pane");
            });

            //이하 active 클래스 추가
            list.setAttribute("class", "postList active");
            tab_pane[index].setAttribute("class", "tab-pane active");

            //이하 list 내부 get
            if (index == 0) {
                recentPost();
            }else if(index == 1){
                recentReply();
            }
        });
    });

    //메인 위젯 최근글들 불러오기
    if(location.pathname == "/") {
        getWidget("Notice", widgetNotice);
        getWidget("Free", widgetFree);
    }
});

function init(){
    recentPost();
}

function recentPost(){
    const tab_pane_content = document.querySelectorAll(".document_content");

    $.ajax({
        async: false,
        type: "get",
        url: "/api/index/recentPost",
        success: (response) => {
            tab_pane_content.forEach((inner, index) => {
                if (0 <= index && index < 5) {
                    let data = response.data[index];

                    inner.innerHTML = `
                        <a href="/${data.tblName}/${data.id}">${data.title}</a>
                    `;
                }
            });
        }, error: (error) => {
            console.log(error);
        }
    });
}

function recentReply(){
    const tab_pane_content = document.querySelectorAll(".document_content");

    $.ajax({
        async: false,
        type: "get",
        url: "/api/index/recentReply",
        success: (response) => {
            console.log(response);
            tab_pane_content.forEach((inner, index) => {
                if (5 <= index && index < 10) {
                    let data = response.data[index-5];

                    inner.innerHTML = `
                        <a href="/${data.board}/${data.postNum}">${data.contents}</a>
                    `;
                }
            });
        }, error: (error) => {
            console.log(error);
        }
    });
}

//메인 위젯 최근글들 불러오기
function getWidget(board, widget) {
    const maxPostsLoad = 10;

    $.ajax({
        async: false,
        type: "get",
        url: "/api/board/" + board + "/getRecentPosts/" + maxPostsLoad,
        success: (response) => {
            response.data.forEach((post, index) => {
                widget.innerHTML += `
                    <li>
                        <a href="Notice/${post.id}" style="font-size:10px">${post.title}</a>
                    </li>
                `;
            })
        }, error: (error) => {
            console.log(error);
        }
    })
}

function logout() {
    location.href = "/logout";
}