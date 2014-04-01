$(function() {

    var board = [];

    // Generate a random number
    function generateRandomNumber() {
        return Math.floor((Math.random()*5)+1);
    }

    // This function initializes the board
    function initializeBoard() {
        for (var row = 0; row < 9; row++) {
            var boardRow = [];
            for (var col = 0; col < 9; col++) {
                boardRow.push(0);
            }
            board.push(boardRow);
        }
    }

    // This function draws the board
    function drawBoard() {
        for (var row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                var rowData = $('<div class="row">');

                for (var col = 0; col < 9; col++) {

                    if (col % 3 == 0 && col != 0) {
                        var dummyDiv = $("<div class='dummy-div-vertical-horizontal'>");
                        //dummyDiv.html("&nbsp;");
                        rowData.append(dummyDiv);
                    }

                    var dummyDiv = $("<div class='dummy-div-vertical'>");
                    //dummyDiv.html("&nbsp;");
                    rowData.append(dummyDiv);
                }

                $('#game-table').append(rowData);
            }

            var rowData = $('<div class="row">');

            for (var col = 0; col < 9; col++) {

                if (col % 3 == 0 && col != 0) {
                    var dummyDiv = $("<div class='dummy-div-horizontal'>");
                    dummyDiv.html("&nbsp;");
                    rowData.append(dummyDiv);
                }

                var divString = "<div class='table-cell-div' id='box-" + row + "-" + col + "'>"
                var cell = $(divString);
                cell.html("&nbsp;");
                rowData.append(cell);
            }

            $('#game-table').append(rowData);
        }
    }

    // This function creates the div which is used to select a number
    function createSelectNumberDiv() {
        var selectNumberDiv = $('<div id="selectNumber">');

        for (var row = 1; row <= 3; row++) {
            var rowData = $('<div class="row">');

            for (var col = 1; col <= 3; col++) {
                var index = (row - 1) * 3 + col;
                var cell = $("<div class='table-cell-div' data-id='" + index + "'>");
                cell.text(index);
                rowData.append(cell);
            }

            selectNumberDiv.append(rowData);
        }

        var specialRow = $('<div class="row">');
        var clearCell = $("<div class='table-cell-special-div' data-id='Clear'>");
        clearCell.text('Clear');
        var cancelCell = $("<div class='table-cell-special-div' data-id='Cancel'>");
        cancelCell.text('Cancel');

        specialRow.append(clearCell);
        specialRow.append(cancelCell);

        selectNumberDiv.append(specialRow);
        return selectNumberDiv;
    }

    var selectedDiv = null;

    var selectNumberDiv = createSelectNumberDiv();
    $('.main-content').append(selectNumberDiv);
    selectNumberDiv.hide();

    drawBoard();
    initializeBoard();

    $('#game-table .table-cell-div').on('click', function() {
        // Show the select number div when clicked
        selectNumberDiv.show();

        // Position the Div according to this Div
        selectNumberDiv.css({'top': $(this).position().top - 50, 'left': $(this).position().left - 30});

        // Set the selected div variable to this
        selectedDiv = this;
    });

    $('#selectNumber .table-cell-div').on('click', function() {

        // Update the board variable based on the number selected
        var splitVal = $(selectedDiv).attr('id').split('-');
        board[parseInt(splitVal[1])][parseInt(splitVal[2])] = parseInt($(this).data('id'));

        // Set the text of the Selected Div
        $(selectedDiv).text($(this).data('id'));

        // Set the color on the selected div
        $(selectedDiv).addClass('table-cell-div-selected-' + generateRandomNumber());

        // Hide the select number div and the Cancel button
        selectNumberDiv.hide();
    });

    $('#selectNumber .table-cell-special-div').on('click', function() {

        if ($(this).data('id') === "Cancel") {
            selectNumberDiv.hide();
        } else if ($(this).data('id') === "Clear") {

            var splitVal = $(selectedDiv).attr('id').split('-');
            board[parseInt(splitVal[1])][parseInt(splitVal[2])] = 0;
            $(selectedDiv).html("&nbsp;");

            for (var i = 1; i <= 5; i++) {
                if ($(selectedDiv).hasClass('table-cell-div-selected-' + i)) {
                    $(selectedDiv).removeClass('table-cell-div-selected-' + i);
                }
            }

        }

        // Hide the select number div and the Cancel button
        selectNumberDiv.hide();
    });

    $('#solve-button').on('click', function() {
        $('.loading-img img').show();
        $('.main-table').hide();

        $.post('/solve', JSON.stringify({ 'data': board }), function(result) {
            $('.loading-img img').hide();
            $('.main-table').show();

            var status = result['status'];

            if (status === 'success') {
                var data = result['data'];
                for (var row = 0; row < data.length; row++) {
                    for (var col=0; col < data[row].length; col++) {
                        $('#box-' + row + "-" + col).text(data[row][col]);
                    }
                }
            } else if (status === 'error') {
                // Handle Errors
                console.log(result);
                $('.loading-img img').hide();
                $('.main-table').show();
                $('#errorModal').foundation('reveal', 'open');
            }
        });
    });
});