import PlayerInfoTab from "./PlayerInfoTab";
import ClubPerformanceTab from "./ClubPerformanceTab";
import NationalPerformanceTab from "./NationalPerformanceTab";

function PlayerModal({
  selectedProfile,
  activeTab,
  setActiveTab,
  closePlayerDetails,
  getTotal,
  addPlayerToFavorites,
  isPlayerFavorite,
}) {
  if (!selectedProfile) return null;

  const player = selectedProfile.player;
  const clubPerformance = selectedProfile.clubPerformance || [];
  const nationalPerformance = selectedProfile.nationalPerformance || [];

  return (
    <div className="player-modal">
      <div className="player-modal-box">
        <button className="modal-close" onClick={closePlayerDetails}>
          ×
        </button>

        <div className="modal-left">
          <img src={player.player_image_url} alt={player.player_name} />

          <h1>{player.player_name}</h1>

          <p>{player.current_club_name}</p>

          <span>{player.position}</span>

          <button
            className="fav-btn modal-fav-btn"
            onClick={() => addPlayerToFavorites(player)}
          >
            {isPlayerFavorite && isPlayerFavorite(player.player_id)
              ? "❤️ Added Favorite"
              : "❤️ Add Favorite"}
          </button>
        </div>

        <div className="modal-right">
          <h2>Player Details</h2>

          <div className="tabs">
            <button
              className={activeTab === "info" ? "tab active-tab" : "tab"}
              onClick={() => setActiveTab("info")}
            >
              Basic Info
            </button>

            <button
              className={activeTab === "club" ? "tab active-tab" : "tab"}
              onClick={() => setActiveTab("club")}
            >
              Club Performance
            </button>

            <button
              className={activeTab === "national" ? "tab active-tab" : "tab"}
              onClick={() => setActiveTab("national")}
            >
              National
            </button>
          </div>

          {activeTab === "info" && <PlayerInfoTab player={player} />}

          {activeTab === "club" && (
            <ClubPerformanceTab
              clubPerformance={clubPerformance}
              getTotal={getTotal}
            />
          )}

          {activeTab === "national" && (
            <NationalPerformanceTab
              nationalPerformance={nationalPerformance}
              player={player}
              getTotal={getTotal}
            />
          )}
        </div>
      </div>
    </div>
  );
}

export default PlayerModal;