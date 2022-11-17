window.addEventListener("load", () => {
    const postList_Nav = document.querySelectorAll(".postList");
    const tab_pane = document.querySelectorAll(".tab-pane");

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
            list.setAttribute("class","postList active");
            tab_pane[index].setAttribute("class","tab-pane active");
        });
    });
})