let selectedCell = null;

// Function to handle selecting a piece
function selectPiece(cell) {
    if (selectedCell) {
        // If a piece is already selected, attempt a move
        const from = selectedCell.getAttribute("data-pos");
        const to = cell.getAttribute("data-pos");

        if (from !== to) {
            makeMove(from, to);
        }

        // Reset selection
        selectedCell.classList.remove("selected");
        selectedCell = null;
    } else {
        // Select the clicked cell
        const piece = cell.querySelector("img");
        if (piece) {
            selectedCell = cell;
            selectedCell.classList.add("selected");
        }
    }
}

// Function to send the move to the backend
function makeMove(from, to) {
    fetch("/move", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({ from: from, to: to }),
    })
        .then(response => response.text())
        .then(result => {
            if (result === "Move successful") {
                alert("Move successful!");
                refreshBoard();
            } else {
                alert("Invalid move. Try again.");
            }
        })
        .catch(error => {
            console.error("Error during move:", error);
        });
}

// Function to refresh the board visually
function refreshBoard() {
    fetch("/")
        .then(response => response.text())
        .then(html => {
            // Replace the chess board's table with the updated one
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, "text/html");
            const newBoard = doc.querySelector(".chess-board table");
            document.querySelector(".chess-board").innerHTML = "";
            document.querySelector(".chess-board").appendChild(newBoard);
        })
        .catch(error => {
            console.error("Error refreshing board:", error);
        });
}
