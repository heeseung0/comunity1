window.addEventListener('load', () => {
    let boardURL = location.href.split('/')[3];
    let postID = location.href.split('/')[4];
    let writer = '';

    $.ajax({
        async: false,
        type: 'get',
        url: "/api/board/" + boardURL + "/" + postID + "/getPost",
        success: (response) => {
            initEditor(response.data.contents);

            document.querySelector(".newPost_type").value = response.data.type;
            document.querySelector(".newPost_title").value = response.data.title;
            writer = response.data.writer;
        }, error: (error) => {
            console.log(error);
        }
    })



    const submit = document.querySelector(".account-submit");

    submit.addEventListener('click',() => {
        console.log("test");
        const boardModifyReqDto = {
            type: document.querySelector(".newPost_type").value,
            title: document.querySelector(".newPost_title").value,
            writer: writer,
            contents: document.querySelector(".ck-content").innerHTML
        }
        let url = "/" + boardURL + "/" + postID + "/PostModify";
        $.ajax({
            async: true,
            type: 'put',
            url: url,
            data: JSON.stringify(boardModifyReqDto),
            contentType: "application/json",
            success: (response) => {
                let replaceUrl = [];
                replaceUrl[0] = location.href.substring(0, location.href.indexOf("?"));
                replaceUrl[1] = location.href.substring(location.href.indexOf("?")+1);

                location.href = "/" + boardURL + "/" + postID + "?" + replaceUrl[1];
            }, error: (error) => {
                console.log(error);
            }
        })
    });

});

function initEditor(contents) {
    ClassicEditor
        .create(document.querySelector('#editor'), {
            initialData: contents,
            toolbar: ['bold', 'italic', 'link', 'bulletedList', 'numberedList']
        })
        .then(editor => {
            console.log('Editor was initialized');
        })
        .catch(err => {
            console.error(err.stack);
        });
}