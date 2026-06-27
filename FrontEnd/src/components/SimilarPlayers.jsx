function SimilarPlayers({
  similarPlayers,
  openPlayerDetails,
}) {
  return (
    <div>
      <h3 className="section-title">
        Similar Players
      </h3>

      <div className="similar-grid">
        {similarPlayers.map((player) => (
          <div
            key={player.player_id}
            className="similar-card"
            onClick={() =>
              openPlayerDetails(
                player.player_id
              )
            }
          >
            <img
              src={
                player.player_image_url
              }
              alt={player.player_name}
            />

            <h4>
              {player.player_name}
            </h4>

            <p>{player.position}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default SimilarPlayers;