document.addEventListener('DOMContentLoaded', function() {
    const pointInput = document.getElementById('pointInput');
    const previewBox = document.getElementById('previewBox');
    const afterPoint = document.getElementById('afterPoint');
    const btnReset = document.getElementById('btnReset');

    if (!pointInput) return;

    const currentPoint = parseInt(pointInput.dataset.currentPoint) || 0;
    document.querySelectorAll('.btn-point-adjust').forEach(button => {
        button.addEventListener('click', function() {
            const val = parseInt(this.dataset.value);
            let currentVal = parseInt(pointInput.value) || 0;
            pointInput.value = currentVal + val;
            calculatePreview();
        });
    });

    pointInput.addEventListener('input', calculatePreview);
    if (btnReset) {
        btnReset.addEventListener('click', function() {
            pointInput.value = "";
            calculatePreview();
        });
    }
    function calculatePreview() {
        let changeAmount = parseInt(pointInput.value) || 0;
        let result = currentPoint + changeAmount;

        if (pointInput.value !== "" && pointInput.value !== "0") {
            previewBox.classList.remove('d-none');
            afterPoint.innerText = result.toLocaleString();

            if (result < 0) {
                afterPoint.classList.replace('text-primary', 'text-danger');
            } else {
                afterPoint.classList.replace('text-danger', 'text-primary');
            }
        } else {
            previewBox.classList.add('d-none');
        }
    }
});