window.addEventListener('load', () => {
    initEditor();
});

function initEditor() {
    ClassicEditor
        .create(document.querySelector('#editor'), {
            initialData: '<h2>Initial data</h2><p>Foo bar.</p>',
            toolbar: ['bold', 'italic', 'link', 'bulletedList', 'numberedList']
        })
        .then(editor => {
            console.log('Editor was initialized');
        })
        .catch(err => {
            console.error(err.stack);
        });
}