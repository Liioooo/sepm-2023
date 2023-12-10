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
    this.chart = new Chart('Top 10 Events', {
      type: 'pie',
      data: {
        labels: [], // You can add labels dynamically if needed
        datasets: [{
          label: 'Top 10 ' + this.eventType + ' in ' + this.searchMonth,
          data: this.chartData,
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
              size: 24,
              weight: 'bold',
              family: '\'Helvetica Neue\', \'Helvetica\', \'Arial\', sans-serif'
            },
            padding: {
              top: 10,
              bottom: 30
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
