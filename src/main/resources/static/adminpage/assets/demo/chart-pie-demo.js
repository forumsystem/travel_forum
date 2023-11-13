// Fetch data from the backend
fetch('/api/data') // Replace '/api/data' with your actual API endpoint
    .then(response => response.json())
    .then(data => {
      // Call a function to create the area chart with the fetched data
      createAreaChart(data);
    })
    .catch(error => console.error('Error fetching data:', error));

// Function to create an area chart
function createAreaChart(data) {
  // Set new default font family and font color
  Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
  Chart.defaults.global.defaultFontColor = '#292b2c';

  // Create an area chart
  var ctx = document.getElementById("myAreaChart").getContext('2d');
  var myAreaChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: data.labels,
      datasets: [{
        label: 'My Dataset',
        data: data.values,
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }],
    },
  });
}
