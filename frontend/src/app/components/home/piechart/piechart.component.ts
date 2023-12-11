import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-piechart',
  templateUrl: './piechart.component.html',
  styleUrls: ['./piechart.component.scss']
})
export class PiechartComponent implements OnInit, OnChanges {
  @Input() searchMonth: string = '';
  @Input() eventType: string = '';
  @Input() chartData: number[] = []; // Input for dynamic data

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.chartData && !changes.chartData.firstChange) {
      // Update the chart when the input data changes
      this.updateChart();
    }
  }

  public chart: any;

  createChart() {
    this.chart = new Chart('MyChart', {
      type: 'pie',
      // type: 'doughnut',
      data: {
        labels: [
          //da gehören die Event names hin
          'Miley Cyrus',
          'Harry Styles Show 2',
          'Harry Styles Show 1',
          'Seiler und Speer',
          'Taylor Swift Eras Vienna',
          'Taylor Swift Eras Graz',
          'Taylor Swift Eras Klagenfurt',
          'John Legend'
        ],

        datasets: [{
          label: 'Top 10 '+ this.eventType + ' in '+ this.searchMonth,
          data: [9168.2, 1417.8, 3335.1, 1165.0, 2078.9, 3533, 5324, 5232, 2324, 2354],
          backgroundColor: [
            'rgb(1,21,104)',
            'rgb(70,61,133)',
            'rgb(117,105,163)',
            'rgb(162,153,193)',
            'rgb(208,203,224)',
            'rgb(255,221,218)',
            'rgb(255,187,182)',
            'rgb(251,153,147)',
            'rgb(244,117,114)',
            'rgb(233,77,82)'
          ],
          hoverOffset: 4

        }]
      },
      options: {
        aspectRatio: 2.5,
        plugins: {
          title: {
            display: true,
            text: 'Top 10 ' + this.eventType + ' in ' + this.searchMonth,
            font: {
              size: 34,
              weight: 'bold',
              family: '\'Helvetica Neue\', \'Helvetica\', \'Arial\', sans-serif'
            },
            padding: {
              top: 40,
              bottom: 40
            }
          },

          legend: {
            display: true,
            labels: {
              font: {
                size: 14,
                family: '\'Helvetica Neue\', \'Helvetica\', \'Arial\', sans-serif'
              }
            }
          }
        }
      }
    });
  }

  updateChart() {
    // Update the chart data when the input data changes
    this.chart.data.datasets[0].data = this.chartData;
    this.chart.update();
  }
}
