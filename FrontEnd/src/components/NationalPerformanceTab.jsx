function NationalPerformanceTab({
  nationalPerformance,
  player,
  getTotal,
}) {
  return (
    <>
      <div className="stats-summary">
        <div>
          <b>Teams</b>
          <p>{nationalPerformance.length}</p>
        </div>

        <div>
          <b>Matches</b>
          <p>{getTotal(nationalPerformance, "matches")}</p>
        </div>

        <div>
          <b>Goals</b>
          <p>{getTotal(nationalPerformance, "goals")}</p>
        </div>

        <div>
          <b>Country</b>
          <p>{player.citizenship || "N/A"}</p>
        </div>
      </div>

      <div className="performance-list">
        {nationalPerformance.map((item) => (
          <div className="performance-row" key={item.id}>
            <div>
              <b>Team ID</b>
              <p>{item.teamId}</p>
            </div>

            <div>
              <b>Matches</b>
              <p>{item.matches}</p>
            </div>

            <div>
              <b>Goals</b>
              <p>{item.goals}</p>
            </div>

            <div>
              <b>Shirt No.</b>
              <p>{item.shirtNumber}</p>
            </div>

            <div>
              <b>Career State</b>
              <p>{item.careerState}</p>
            </div>
          </div>
        ))}
      </div>
    </>
  );
}

export default NationalPerformanceTab;