import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Cell,
} from "recharts";

function PerformanceChart({
  clubPerformance,
  getTotal,
}) {
  const data = [
    {
      name: "Goals",
      value: getTotal(
        clubPerformance,
        "goals"
      ),
      color: "#39ff88",
    },
    {
      name: "Assists",
      value: getTotal(
        clubPerformance,
        "assists"
      ),
      color: "#3b82f6",
    },
    {
      name: "Minutes",
      value: getTotal(
        clubPerformance,
        "minutesPlayed"
      ),
      color: "#f59e0b",
    },
    {
      name: "Yellow",
      value: getTotal(
        clubPerformance,
        "yellowCards"
      ),
      color: "#fde047",
    },
    {
      name: "Red",
      value: getTotal(
        clubPerformance,
        "directRedCards"
      ),
      color: "#ef4444",
    },
  ];

  return (
    <div className="chart-container">
      <h3>Performance Overview</h3>

      <ResponsiveContainer
        width="100%"
        height={350}
      >
        <BarChart data={data}>
          <XAxis
            dataKey="name"
            tick={{
              fill: "#ffffff",
              fontSize: 14,
              fontWeight: 700,
            }}
          />

          <YAxis
            tick={{
              fill: "#ffffff",
              fontSize: 13,
            }}
          />

          <Tooltip
            contentStyle={{
              background:
                "#0f172a",
              border:
                "1px solid #334155",
              borderRadius:
                "12px",
              color: "white",
            }}
          />

          <Bar
            dataKey="value"
            radius={[12, 12, 0, 0]}
          >
            {data.map(
              (entry, index) => (
                <Cell
                  key={index}
                  fill={entry.color}
                />
              )
            )}
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}

export default PerformanceChart;