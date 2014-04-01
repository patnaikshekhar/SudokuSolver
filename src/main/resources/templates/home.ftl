<!DOCTYPE html>
<html>
    <head>
        <title>Sudoku Solver</title>
        <link rel="stylesheet" href="/lib/foundation/css/foundation.min.css" />
        <link rel="stylesheet" href="/css/home.css" />
        <script src="/lib/jquery/jquery.min.js"></script>
        <script src="/lib/foundation/js/foundation.min.js"></script>
        <script src="/js/home.js"></script>
    </head>
    <body>
        <nav class="top-bar" data-topbar>
            <ul class="title-area">
                <l1 class="name">
                    <h1><a href="#">Sudoku Solver</a></h1>
                </l1>
            </ul>
            <section class="top-bar-section">
                <ul class="right">
                    <li class="has-form">
                        <a href="#" class="button" id="solve-button">Solve</a>
                    </li>
                </ul>
            </section>
        </nav>
        <div class="main-content">
            <div class="main-table">
                <div id="game-table">
                </div>
            </div>
            <div class="loading-img">
                <img src="/img/loader.gif" />
            </div>
            <div id="errorModal" class="reveal-modal" data-reveal>
                <h2>Woops!</h2>
                <p class="lead">An error occurred - could not solve the puzzle</p>
                <a class="close-reveal-modal">&#215;</a>
            </div>
        </div>
    </body>
</html>