import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

import PlayerModal from "./components/PlayerModal";

function App() {
  const [activePage, setActivePage] = useState("players");

  const [players, setPlayers] = useState([]);
  const [search, setSearch] = useState("");
  const [searchType, setSearchType] = useState("name");
  const [page, setPage] = useState(0);

  const [selectedProfile, setSelectedProfile] = useState(null);
  const [activeTab, setActiveTab] = useState("info");

  const [clubs, setClubs] = useState([]);
  const [clubSearch, setClubSearch] = useState("");
  const [selectedClub, setSelectedClub] = useState(null);
  const [clubData, setClubData] = useState(null);
  const [clubTab, setClubTab] = useState("overview");

  const [favoritePlayers, setFavoritePlayers] = useState(() => {
    return JSON.parse(localStorage.getItem("favoritePlayers")) || [];
  });

  const [favoriteClubs, setFavoriteClubs] = useState(() => {
    return JSON.parse(localStorage.getItem("favoriteClubs")) || [];
  });

  const [showFavorites, setShowFavorites] = useState(false);
  const [favoriteTab, setFavoriteTab] = useState("players");

  useEffect(() => {
    fetchPlayers(0);
    fetchClubs();
  }, []);

  const fetchPlayers = (pageNumber = 0) => {
    axios
      .get(`http://localhost:8080/players?page=${pageNumber}&size=12`)
      .then((res) => {
        setPlayers(res.data.content);
        setPage(pageNumber);
      })
      .catch((err) => console.log(err));
  };

  const searchPlayers = () => {
    if (search.trim() === "") {
      fetchPlayers(0);
      return;
    }

    let url = "";

    if (searchType === "name") {
      url = `http://localhost:8080/players/search?name=${search}`;
    } else if (searchType === "club") {
      url = `http://localhost:8080/players/club?club=${search}`;
    } else if (searchType === "position") {
      url = `http://localhost:8080/players/position?position=${search}`;
    } else if (searchType === "id") {
      url = `http://localhost:8080/players/${search}`;
    }

    axios
      .get(url)
      .then((res) => {
        if (searchType === "id") {
          setPlayers(res.data ? [res.data] : []);
        } else {
          setPlayers(res.data);
        }
      })
      .catch((err) => {
        console.log(err);
        setPlayers([]);
      });
  };

  const clearSearch = () => {
    setSearch("");
    setSearchType("name");
    fetchPlayers(0);
  };

  const openPlayerDetails = (playerId) => {
    setActiveTab("info");

    axios
      .get(`http://localhost:8080/player/profile/${playerId}`)
      .then((res) => {
        setSelectedProfile(res.data);
      })
      .catch((err) => console.log(err));
  };

  const closePlayerDetails = () => {
    setSelectedProfile(null);
    setActiveTab("info");
  };

  const getTotal = (arr, field) => {
    if (!arr || arr.length === 0) return 0;

    return arr.reduce((sum, item) => {
      return sum + (item[field] || 0);
    }, 0);
  };

  const fetchClubs = () => {
    axios
      .get("http://localhost:8080/league-tables")
      .then((res) => {
        const latestClubs = {};

        res.data.forEach((club) => {
          if (
            !latestClubs[club.teamName] ||
            club.seasonYear > latestClubs[club.teamName].seasonYear
          ) {
            latestClubs[club.teamName] = club;
          }
        });

        setClubs(Object.values(latestClubs));
      })
      .catch((err) => console.log(err));
  };

  const searchClubs = () => {
    if (clubSearch.trim() === "") {
      fetchClubs();
      return;
    }

    axios
      .get(`http://localhost:8080/league-tables/team?team=${clubSearch}`)
      .then((res) => {
        const latestClubs = {};

        res.data.forEach((club) => {
          if (
            !latestClubs[club.teamName] ||
            club.seasonYear > latestClubs[club.teamName].seasonYear
          ) {
            latestClubs[club.teamName] = club;
          }
        });

        setClubs(Object.values(latestClubs));
      })
      .catch((err) => {
        console.log(err);
        setClubs([]);
      });
  };

  const clearClubSearch = () => {
    setClubSearch("");
    fetchClubs();
  };

  const openClubDetails = (club) => {
    setSelectedClub(club);
    setClubTab("overview");
    setClubData(null);

    Promise.all([
      axios.get(`http://localhost:8080/league-tables/team?team=${club.teamName}`),
      axios.get(`http://localhost:8080/matches/club?name=${club.teamName}`),
      axios.get(`http://localhost:8080/goal-leaders/team?team=${club.teamName}`),
      axios.get(`http://localhost:8080/assist-leaders/team?team=${club.teamName}`),
      axios.get(`http://localhost:8080/team-discipline/team?team=${club.teamName}`),
      axios.get(`http://localhost:8080/players/club?club=${club.teamName}`),
    ])
      .then(([tables, matches, goals, assists, discipline, players]) => {
        setClubData({
          tables: tables.data || [],
          matches: matches.data || [],
          goals: goals.data || [],
          assists: assists.data || [],
          discipline: discipline.data || [],
          players: players.data || [],
        });
      })
      .catch((err) => console.log(err));
  };

  const closeClubDetails = () => {
    setSelectedClub(null);
    setClubData(null);
    setClubTab("overview");
  };

  const getLatest = (arr) => {
    if (!arr || arr.length === 0) return null;
    return [...arr].sort((a, b) => b.seasonYear - a.seasonYear)[0];
  };

  const addPlayerToFavorites = (player) => {
    const exists = favoritePlayers.find(
      (p) => p.player_id === player.player_id
    );

    if (exists) return;

    const updated = [...favoritePlayers, player];

    setFavoritePlayers(updated);
    localStorage.setItem("favoritePlayers", JSON.stringify(updated));
  };

  const addClubToFavorites = (club) => {
    const exists = favoriteClubs.find(
      (c) => c.teamName === club.teamName
    );

    if (exists) return;

    const updated = [...favoriteClubs, club];

    setFavoriteClubs(updated);
    localStorage.setItem("favoriteClubs", JSON.stringify(updated));
  };

  const removePlayerFavorite = (id) => {
    const updated = favoritePlayers.filter(
      (p) => p.player_id !== id
    );

    setFavoritePlayers(updated);
    localStorage.setItem("favoritePlayers", JSON.stringify(updated));
  };

  const removeClubFavorite = (name) => {
    const updated = favoriteClubs.filter(
      (c) => c.teamName !== name
    );

    setFavoriteClubs(updated);
    localStorage.setItem("favoriteClubs", JSON.stringify(updated));
  };

  const isPlayerFavorite = (id) => {
    return favoritePlayers.some((p) => p.player_id === id);
  };

  const isClubFavorite = (name) => {
    return favoriteClubs.some((c) => c.teamName === name);
  };

  const latestTable = clubData ? getLatest(clubData.tables) : selectedClub;
  const latestDiscipline = clubData ? getLatest(clubData.discipline) : null;

  return (
    <div className="app">
      <aside className="sidebar">
        <div>
          <div className="logo">
            ⚽ Foot<span>Buzz</span>
          </div>

          <nav>
            <p
              className={activePage === "players" ? "active" : ""}
              onClick={() => setActivePage("players")}
            >
              Players
            </p>

            <p
              className={activePage === "clubs" ? "active" : ""}
              onClick={() => setActivePage("clubs")}
            >
              Clubs
            </p>

            <p>Competitions</p>
            <p>Transfers</p>
            <p>Stats</p>
            <p>Favorites</p>
          </nav>
        </div>

        <div className="side-card">
          <div className="ball">⚽</div>
          <h3>Live the game.</h3>
          <h3>Love the data.</h3>
          <span>FootBuzz</span>
        </div>
      </aside>

      {activePage === "players" && (
        <main className="main">
          <div className="topbar">
            <button className="icon-btn">🌙</button>
            <button className="fav-btn" onClick={() => setShowFavorites(true)}>
              ⭐ My Favorites
            </button>
          </div>

          <section className="hero">
            <h1>
              Discover <span>Amazing</span> Players
            </h1>

            <p>Search football profiles by name, ID, club, or position.</p>

            <div className="search-box">
              <select
                value={searchType}
                onChange={(e) => setSearchType(e.target.value)}
              >
                <option value="name">Name</option>
                <option value="id">ID</option>
                <option value="club">Club</option>
                <option value="position">Position</option>
              </select>

              <input
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder={
                  searchType === "id"
                    ? "Enter player ID..."
                    : `Search by ${searchType}...`
                }
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    searchPlayers();
                  }
                }}
              />

              <button onClick={searchPlayers}>Search</button>

              <button onClick={clearSearch} className="clear">
                Clear
              </button>
            </div>
          </section>

          <section className="grid">
            {players.map((player, index) => (
              <div
                className="card"
                key={player.player_id}
                onClick={() => openPlayerDetails(player.player_id)}
              >
                <div className="rating">{94 - (index % 8)}</div>

                <button
                  className="heart"
                  onClick={(e) => {
                    e.stopPropagation();
                    addPlayerToFavorites(player);
                  }}
                >
                  {isPlayerFavorite(player.player_id) ? "❤️" : "♡"}
                </button>

                <div className="image-wrap">
                  <img
                    src={player.player_image_url}
                    alt={player.player_name}
                  />
                </div>

                <div className="card-body">
                  <h2>{player.player_name}</h2>

                  <p className="club">{player.current_club_name}</p>

                  <div className="meta">
                    <span>Age: {player.age}</span>
                    <span>{player.foot}</span>
                  </div>

                  <p className="position">{player.position}</p>
                </div>
              </div>
            ))}
          </section>

          <div className="pagination">
            <button onClick={() => fetchPlayers(Math.max(page - 1, 0))}>
              Prev
            </button>

            <span>Page {page + 1}</span>

            <button onClick={() => fetchPlayers(page + 1)}>Next</button>
          </div>
        </main>
      )}

      {activePage === "clubs" && (
        <main className="main">
          <div className="topbar">
            <button className="icon-btn">🌙</button>
            <button className="fav-btn" onClick={() => setShowFavorites(true)}>
              ⭐ My Favorites
            </button>
          </div>

          <section className="hero">
            <h1>
              Explore <span>Elite</span> Clubs
            </h1>

            <p>Search clubs and view standings, matches, leaders and discipline.</p>

            <div className="club-search-box">
              <input
                value={clubSearch}
                onChange={(e) => setClubSearch(e.target.value)}
                placeholder="Search club like Arsenal, Liverpool, Barcelona..."
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    searchClubs();
                  }
                }}
              />

              <button onClick={searchClubs}>Search</button>

              <button onClick={clearClubSearch} className="clear">
                Clear
              </button>
            </div>
          </section>

          <section className="club-grid">
            {clubs.map((club, index) => (
              <div
                className="club-card"
                key={`${club.teamName}-${index}`}
                onClick={() => openClubDetails(club)}
              >
                <button
                  className="heart"
                  onClick={(e) => {
                    e.stopPropagation();
                    addClubToFavorites(club);
                  }}
                >
                  {isClubFavorite(club.teamName) ? "❤️" : "♡"}
                </button>

                <div className="club-rank">#{club.place}</div>

                <div className="club-logo">
                  {club.teamName ? club.teamName.charAt(0).toUpperCase() : "C"}
                </div>

                <h2>{club.teamName}</h2>

                <p className="club">{club.leagueName}</p>

                <div className="club-mini">
                  <div>
                    <b>{club.points}</b>
                    <span>PTS</span>
                  </div>

                  <div>
                    <b>{club.wins}</b>
                    <span>W</span>
                  </div>

                  <div>
                    <b>{club.goalDifference}</b>
                    <span>GD</span>
                  </div>
                </div>

                <p className="position">Season {club.seasonYear}</p>
              </div>
            ))}
          </section>
        </main>
      )}

      <PlayerModal
        selectedProfile={selectedProfile}
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        closePlayerDetails={closePlayerDetails}
        getTotal={getTotal}
        addPlayerToFavorites={addPlayerToFavorites}
        isPlayerFavorite={isPlayerFavorite}
      />

      {selectedClub && (
        <div className="player-modal">
          <div className="club-modal-box">
            <button className="modal-close" onClick={closeClubDetails}>
              ×
            </button>

            <div className="modal-left">
              <div className="club-big-logo">
                {selectedClub.teamName
                  ? selectedClub.teamName.charAt(0).toUpperCase()
                  : "C"}
              </div>

              <h1>{selectedClub.teamName}</h1>

              <p>{latestTable?.leagueName || selectedClub.leagueName}</p>

              <span>Season {latestTable?.seasonYear || selectedClub.seasonYear}</span>

              <button
                className="fav-btn modal-fav-btn"
                onClick={() => addClubToFavorites(selectedClub)}
              >
                {isClubFavorite(selectedClub.teamName)
                  ? "❤️ Added Favorite"
                  : "❤️ Add Favorite"}
              </button>
            </div>

            <div className="modal-right">
              <h2>Club Dashboard</h2>

              <div className="tabs">
                <button
                  className={clubTab === "overview" ? "tab active-tab" : "tab"}
                  onClick={() => setClubTab("overview")}
                >
                  Overview
                </button>

                <button
                  className={clubTab === "matches" ? "tab active-tab" : "tab"}
                  onClick={() => setClubTab("matches")}
                >
                  Matches
                </button>

                <button
                  className={clubTab === "leaders" ? "tab active-tab" : "tab"}
                  onClick={() => setClubTab("leaders")}
                >
                  Leaders
                </button>

                <button
                  className={clubTab === "players" ? "tab active-tab" : "tab"}
                  onClick={() => setClubTab("players")}
                >
                  Players
                </button>
              </div>

              {!clubData && <p className="no-data">Loading club data...</p>}

              {clubData && clubTab === "overview" && (
                <>
                  <div className="career-summary">
                    <div className="summary-card">
                      <h3>🏆 Points</h3>
                      <p>{latestTable?.points || 0}</p>
                    </div>

                    <div className="summary-card">
                      <h3>✅ Wins</h3>
                      <p>{latestTable?.wins || 0}</p>
                    </div>

                    <div className="summary-card">
                      <h3>🤝 Draws</h3>
                      <p>{latestTable?.draws || 0}</p>
                    </div>

                    <div className="summary-card">
                      <h3>❌ Losses</h3>
                      <p>{latestTable?.losses || 0}</p>
                    </div>

                    <div className="summary-card">
                      <h3>⚽ Goals For</h3>
                      <p>{latestTable?.goalsFor || 0}</p>
                    </div>

                    <div className="summary-card">
                      <h3>🛡 Goals Against</h3>
                      <p>{latestTable?.goalsAgainst || 0}</p>
                    </div>
                  </div>

                  <h3 className="section-title">Discipline</h3>

                  <div className="detail-grid">
                    <div>
                      <b>Yellow Cards</b>
                      <p>{latestDiscipline?.yellowCards || "N/A"}</p>
                    </div>

                    <div>
                      <b>Red Cards</b>
                      <p>{latestDiscipline?.redCards || "N/A"}</p>
                    </div>

                    <div>
                      <b>Discipline Points</b>
                      <p>{latestDiscipline?.disciplinePoints || "N/A"}</p>
                    </div>

                    <div>
                      <b>Matches Played</b>
                      <p>
                        {latestDiscipline?.matchesPlayed ||
                          latestTable?.gamesPlayed ||
                          "N/A"}
                      </p>
                    </div>
                  </div>
                </>
              )}

              {clubData && clubTab === "matches" && (
                <>
                  <h3 className="section-title">Recent Matches</h3>

                  <div className="club-match-list">
                    {clubData.matches.slice(0, 20).map((match) => (
                      <div className="club-match-row" key={match.matchId}>
                        <div>
                          <b>{match.matchDate || "N/A"}</b>
                          <p>{match.leagueName}</p>
                        </div>

                        <div>
                          <b>
                            {match.homeTeam} vs {match.awayTeam}
                          </b>
                          <p>{match.venue || "Venue N/A"}</p>
                        </div>

                        <div>
                          <b>
                            {match.homeScore} - {match.awayScore}
                          </b>
                          <p>{match.result}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </>
              )}

              {clubData && clubTab === "leaders" && (
                <>
                  <h3 className="section-title">Top Goal Scorers</h3>

                  <div className="leader-list">
                    {clubData.goals.slice(0, 8).map((item) => (
                      <div className="leader-row" key={item.goalLeaderId}>
                        <b>#{item.rankNo}</b>
                        <span>{item.playerName}</span>
                        <span>{item.goals} Goals</span>
                        <span>{item.seasonYear}</span>
                      </div>
                    ))}
                  </div>

                  <h3 className="section-title">Top Assist Providers</h3>

                  <div className="leader-list">
                    {clubData.assists.slice(0, 8).map((item) => (
                      <div className="leader-row" key={item.assistLeaderId}>
                        <b>#{item.rankNo}</b>
                        <span>{item.playerName}</span>
                        <span>{item.assists} Assists</span>
                        <span>{item.seasonYear}</span>
                      </div>
                    ))}
                  </div>
                </>
              )}

              {clubData && clubTab === "players" && (
                <>
                  <h3 className="section-title">Club Players</h3>

                  <div className="similar-grid">
                    {clubData.players.slice(0, 18).map((player) => (
                      <div
                        className="similar-card"
                        key={player.player_id}
                        onClick={() => openPlayerDetails(player.player_id)}
                      >
                        <img
                          src={player.player_image_url}
                          alt={player.player_name}
                        />

                        <h4>{player.player_name}</h4>

                        <p>{player.position}</p>

                        <button
                          className="fav-small-btn"
                          onClick={(e) => {
                            e.stopPropagation();
                            addPlayerToFavorites(player);
                          }}
                        >
                          {isPlayerFavorite(player.player_id)
                            ? "❤️ Added"
                            : "❤️ Add"}
                        </button>
                      </div>
                    ))}
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
      )}

      {showFavorites && (
        <div className="player-modal">
          <div className="favorites-modal-box">
            <button
              className="modal-close"
              onClick={() => setShowFavorites(false)}
            >
              ×
            </button>

            <div className="modal-right">
              <h2>⭐ My Favorites</h2>

              <div className="tabs">
                <button
                  className={
                    favoriteTab === "players" ? "tab active-tab" : "tab"
                  }
                  onClick={() => setFavoriteTab("players")}
                >
                  Players ({favoritePlayers.length})
                </button>

                <button
                  className={
                    favoriteTab === "clubs" ? "tab active-tab" : "tab"
                  }
                  onClick={() => setFavoriteTab("clubs")}
                >
                  Clubs ({favoriteClubs.length})
                </button>
              </div>

              {favoriteTab === "players" && favoritePlayers.length === 0 && (
                <p className="no-data">No favorite players added yet.</p>
              )}

              {favoriteTab === "players" && favoritePlayers.length > 0 && (
                <div className="similar-grid">
                  {favoritePlayers.map((player) => (
                    <div
                      key={player.player_id}
                      className="similar-card"
                      onClick={() => {
                        setShowFavorites(false);
                        openPlayerDetails(player.player_id);
                      }}
                    >
                      <img
                        src={player.player_image_url}
                        alt={player.player_name}
                      />

                      <h4>{player.player_name}</h4>

                      <p>{player.position}</p>

                      <button
                        className="fav-small-btn remove"
                        onClick={(e) => {
                          e.stopPropagation();
                          removePlayerFavorite(player.player_id);
                        }}
                      >
                        Remove
                      </button>
                    </div>
                  ))}
                </div>
              )}

              {favoriteTab === "clubs" && favoriteClubs.length === 0 && (
                <p className="no-data">No favorite clubs added yet.</p>
              )}

              {favoriteTab === "clubs" && favoriteClubs.length > 0 && (
                <div className="favorite-club-grid">
                  {favoriteClubs.map((club) => (
                    <div
                      key={club.teamName}
                      className="favorite-club-card"
                      onClick={() => {
                        setShowFavorites(false);
                        openClubDetails(club);
                      }}
                    >
                      <div className="club-logo small">
                        {club.teamName
                          ? club.teamName.charAt(0).toUpperCase()
                          : "C"}
                      </div>

                      <h4>{club.teamName}</h4>

                      <p>{club.leagueName}</p>

                      <button
                        className="fav-small-btn remove"
                        onClick={(e) => {
                          e.stopPropagation();
                          removeClubFavorite(club.teamName);
                        }}
                      >
                        Remove
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;