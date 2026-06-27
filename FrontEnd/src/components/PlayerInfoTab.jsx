function PlayerInfoTab({ player }) {
  return (
    <div className="detail-grid">
      <div><b>Player ID</b><p>{player.player_id || "N/A"}</p></div>
      <div><b>Age</b><p>{player.age || "N/A"}</p></div>
      <div><b>Date of Birth</b><p>{player.date_of_birth || "N/A"}</p></div>
      <div><b>Place of Birth</b><p>{player.place_of_birth || "N/A"}</p></div>
      <div><b>Country of Birth</b><p>{player.country_of_birth || "N/A"}</p></div>
      <div><b>Citizenship</b><p>{player.citizenship || "N/A"}</p></div>
      <div><b>Height</b><p>{player.height || "N/A"}</p></div>
      <div><b>Foot</b><p>{player.foot || "N/A"}</p></div>
      <div><b>Main Position</b><p>{player.main_position || "N/A"}</p></div>
      <div><b>Club ID</b><p>{player.current_club_id || "N/A"}</p></div>
      <div><b>Club Name</b><p>{player.current_club_name || "N/A"}</p></div>
      <div><b>Joined</b><p>{player.joined || "N/A"}</p></div>
      <div><b>EU Player</b><p>{player.is_eu ? "Yes" : "No"}</p></div>
    </div>
  );
}

export default PlayerInfoTab;