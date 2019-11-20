import axios from "axios";
import * as Path from "./paths";

const API = axios.create({
  baseURL: Path.API_HEROKU_URL
});

function nextMove(gameState) {
  return new Promise((resolve, reject) => {
    API.post(Path.NEXT_MOVE, gameState)
      .then(response => {
        resolve(response.data);
      })
      .catch(error => {
        reject(error);
      });
  });
}

export default {
  nextMove
};
