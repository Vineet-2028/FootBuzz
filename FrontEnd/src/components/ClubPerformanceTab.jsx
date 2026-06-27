import PerformanceChart from "./PerformanceChart";

function ClubPerformanceTab({
  clubPerformance,
  getTotal,
}) {
  return (
    <>
      <div className="career-summary">
        <div className="summary-card">
          <h3>⚽ Goals</h3>
          <p>
            {getTotal(
              clubPerformance,
              "goals"
            )}
          </p>
        </div>

        <div className="summary-card">
          <h3>🎯 Assists</h3>
          <p>
            {getTotal(
              clubPerformance,
              "assists"
            )}
          </p>
        </div>

        <div className="summary-card">
          <h3>⏱ Minutes</h3>
          <p>
            {getTotal(
              clubPerformance,
              "minutesPlayed"
            )}
          </p>
        </div>

        <div className="summary-card">
          <h3>🟨 Yellow</h3>
          <p>
            {getTotal(
              clubPerformance,
              "yellowCards"
            )}
          </p>
        </div>

        <div className="summary-card">
          <h3>🟥 Red</h3>
          <p>
            {getTotal(
              clubPerformance,
              "directRedCards"
            )}
          </p>
        </div>

        <div className="summary-card">
          <h3>🏆 Matches</h3>
          <p>{clubPerformance.length}</p>
        </div>
      </div>

      <PerformanceChart
        clubPerformance={clubPerformance}
        getTotal={getTotal}
      />

      <h3 className="section-title">
        Recent Matches
      </h3>

      <div className="matches-table">
        <div className="table-header">
          <span>Date</span>
          <span>Competition</span>
          <span>Goals</span>
          <span>Assists</span>
          <span>Minutes</span>
        </div>

        {clubPerformance
          .slice(0, 15)
          .map((match) => (
            <div
              className="table-row"
              key={match.id}
            >
              <span>
                {match.matchDate ||
                  "N/A"}
              </span>

              <span>
                {match.competitionId ||
                  "N/A"}
              </span>

              <span>
                {match.goals || 0}
              </span>

              <span>
                {match.assists || 0}
              </span>

              <span>
                {match.minutesPlayed ||
                  0}
              </span>
            </div>
          ))}
      </div>
    </>
  );
}

export default ClubPerformanceTab;