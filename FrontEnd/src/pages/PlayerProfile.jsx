import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "../App.css";

function PlayerProfile() {
  const { playerId } = useParams();
  const navigate = useNavigate();
  const [player, setPlayer] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/players/${playerId}`)
      .then((res) => setPlayer(res.data))
      .catch((err) => console.log(err));
  }, [playerId]);

  if (!player) {
    return (
      <div className="profile-page">
        <h2>Loading player...</h2>
      </div>
    );
  }

  return (
    <div className="profile-page">
      <button className="back-btn" onClick={() => navigate("/")}>
        ← Back to Players
      </button>

      <div className="profile-card">
        <div className="profile-left">
          <img src={player.player_image_url} alt={player.player_name} />

          <h1>{player.player_name}</h1>
          <p>{player.current_club_name}</p>

          <div className="profile-badge">{player.position}</div>
        </div>

        <div className="profile-right">
          <h2>Player Information</h2>

          <div className="info-grid">
            <p>
              <b>Player ID:</b> {player.player_id}
            </p>

            <p>
              <b>Age:</b> {player.age}
            </p>

            <p>
              <b>Date of Birth:</b> {player.date_of_birth}
            </p>

            <p>
              <b>Place of Birth:</b> {player.place_of_birth}
            </p>

            <p>
              <b>Country of Birth:</b> {player.country_of_birth}
            </p>

            <p>
              <b>Citizenship:</b> {player.citizenship}
            </p>

            <p>
              <b>Height:</b> {player.height}
            </p>

            <p>
              <b>Foot:</b> {player.foot}
            </p>

            <p>
              <b>Position:</b> {player.position}
            </p>

            <p>
              <b>Main Position:</b> {player.main_position}
            </p>

            <p>
              <b>Club ID:</b> {player.current_club_id}
            </p>

            <p>
              <b>Club Name:</b> {player.current_club_name}
            </p>

            <p>
              <b>Joined:</b> {player.joined}
            </p>

            <p>
              <b>EU Player:</b> {player.is_eu ? "Yes" : "No"}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PlayerProfile;