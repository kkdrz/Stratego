function countPointsHorizontally(cells, x, y) {
  var player = cells[x][y];
  var lineClosed = true;
  for (var i = 0; i < cells[x].length; i++) {
    if (cells[x][i] === 0) {
      lineClosed = false;
    }
  }

  var points = 1;
  if (lineClosed) {
    for (var i = y + 1; i < cells[x].length; i++) {
      if (cells[x][i] === player) {
        points++;
      } else {
        break;
      }
    }

    for (var i = y - 1; i >= 0; i--) {
      if (cells[x][i] === player) {
        points++;
      } else {
        break;
      }
    }
  }
  return points >= 2 ? points : 0;
}

function countPointsVertically(cells, x, y) {
  var player = cells[x][y];
  var lineClosed = true;
  for (var i = 0; i < cells.length; i++) {
    if (cells[i][y] === 0) {
      lineClosed = false;
    }
  }

  var points = 1;
  if (lineClosed) {
    for (var i = x + 1; i < cells.length; i++) {
      if (cells[i][y] === player) {
        points++;
      } else {
        break;
      }
    }

    for (var i = x - 1; i >= 0; i--) {
      if (cells[i][y] === player) {
        points++;
      } else {
        break;
      }
    }
  }
  return points >= 2 ? points : 0;
}

function countPointsDiagonallyLeftToTop(cells, x, y) {
  var player = cells[x][y];
  var diagonalClosed = true;
  var topDiagonal = x + y <= cells.length - 1;
  if (topDiagonal) {
    for (var i = x + y, j = 0; i >= 0; i--, j++) {
      if (cells[i][j] === 0) {
        diagonalClosed = false;
      }
    }
  } else {
    for (
      var i = cells.length - 1, j = y - (i - x);
      j < cells.length;
      i--, j++
    ) {
      if (cells[i][j] === 0) {
        diagonalClosed = false;
      }
    }
  }

  var points = 1;
  if (diagonalClosed) {
    for (
      var i = x - 1, j = y + 1;
      j >= 0 && j < cells.length && i >= 0 && i < cells.length;
      i--, j++
    ) {
      if (cells[i][j] === player) {
        points++;
      } else {
        break;
      }
    }
    for (
      var i = x + 1, j = y - 1;
      j >= 0 && j < cells.length && i >= 0 && i < cells.length;
      i++, j--
    ) {
      if (cells[i][j] === player) {
        points++;
      } else {
        break;
      }
    }
  }
  return points >= 2 ? points : 0;
}

function countPointsDiagonallyLeftToBottom(cells, x, y) {
  var player = cells[x][y];
  var diagonalClosed = true;
  var topDiagonal = y >= x;
  if (topDiagonal) {
    for (var i = 0, j = y - x; j < cells.length && i < cells.length; i++, j++) {
      if (cells[i][j] === 0) {
        diagonalClosed = false;
      }
    }
  } else {
    for (var i = x - y, j = 0; i < cells.length && j < cells.length; i++, j++) {
      if (cells[i][j] === 0) {
        diagonalClosed = false;
      }
    }
  }

  var points = 1;
  if (diagonalClosed) {
    for (
      var i = x + 1, j = y + 1;
      j >= 0 && j < cells.length && i >= 0 && i < cells.length;
      i++, j++
    ) {
      if (cells[i][j] === player) {
        points++;
      } else {
        break;
      }
    }

    for (
      var i = x - 1, j = y - 1;
      j >= 0 && j < cells.length && i >= 0 && i < cells.length;
      i--, j--
    ) {
      if (cells[i][j] === player) {
        points++;
      } else {
        break;
      }
    }
  }
  return points >= 2 ? points : 0;
}

function isMovePossible(cells) {
  var possible = false;
  for (var i = 0; i < cells.length; i++) {
    for (var j = 0; j < cells.length; j++) {
      if (Number(cells[i][j]) === Number(0)) {
        possible = true;
      }
    }
  }
  return possible;
}

function countPoints(cells, x, y) {
  var horizontally = this.countPointsHorizontally(cells, x, y);
  var vertically = this.countPointsVertically(cells, x, y);
  var diagonallyLT = this.countPointsDiagonallyLeftToTop(cells, x, y);
  var diagonallyLB = this.countPointsDiagonallyLeftToBottom(cells, x, y);

  var newPoints = horizontally > 1 ? horizontally : 0;
  newPoints += vertically > 1 ? vertically : 0;
  newPoints += diagonallyLT > 1 ? diagonallyLT : 0;
  newPoints += diagonallyLB > 1 ? diagonallyLB : 0;
  return newPoints;
}

export default {
  countPointsVertically,
  countPointsHorizontally,
  countPointsDiagonallyLeftToTop,
  countPointsDiagonallyLeftToBottom,
  isMovePossible,
  countPoints
};
