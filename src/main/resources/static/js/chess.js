// Function to handle selecting a piece
let selectedCell = null;

function selectPiece(cell) {
    const pos = cell.getAttribute("data-pos");
    if (selectedCell) {
        // If a piece is already selected, attempt a move
        const from = selectedCell.getAttribute("data-pos");
        makeMove(from, pos);
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
    fetch("/chess/api/move", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({ from: from, to: to }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Refresh the board after a successful move
                fetchBoard();
            } else {
                alert("Invalid move. Try again.");
            }
        })
        .catch(error => {
            console.error("Error during move:", error);
        });
}

// Function to fetch the current board state from the backend
function fetchBoard() {
    fetch("/chess/api/board", {
        method: "GET", // Explicitly specifying the GET method
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Update the turn display
            document.getElementById("turn").innerText = data.turn;

            // Re-render the board
            const boardElement = document.getElementById("chess-board");
            boardElement.innerHTML = ""; // Clear the current board

            data.board.forEach((row, rowIndex) => {
                const rowElement = document.createElement("tr");
                row.forEach((cell, colIndex) => {
                    const cellElement = document.createElement("td");
                    cellElement.setAttribute("data-pos", `${String.fromCharCode(97 + colIndex)}${8 - rowIndex}`);
                    cellElement.classList.add("chess-cell");

                    if (cell) {
                        const img = document.createElement("img");
                        img.src = `/images/${getPieceImage(cell)}.png`;
                        cellElement.appendChild(img);
                    }

                    cellElement.addEventListener("click", () => selectPiece(cellElement));
                    rowElement.appendChild(cellElement);
                });
                boardElement.appendChild(rowElement);
            });
        })
        .catch(error => {
            console.error("Error fetching board:", error);
        });
}

// Function to get the image name for a piece
function getPieceImage(piece) {
    return piece.toLowerCase() + (piece === piece.toUpperCase() ? "_white" : "_black");
}

// Load the board on page load
document.addEventListener("DOMContentLoaded", () => {
    fetchBoard();
});
