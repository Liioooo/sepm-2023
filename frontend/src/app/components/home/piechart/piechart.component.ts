import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import Chart from 'chart.js/auto';
import { EventWithBoughtCountDto } from '../../../dtos/event-with-bought-count-dto';

@Component({
  selector: 'app-piechart',
  templateUrl: './piechart.component.html',
  styleUrls: ['./piechart.component.scss']
})
export class PiechartComponent implements OnChanges {
  @Input() searchMonth: string = '';
  @Input() eventType: string = '';
  @Input() chartData: EventWithBoughtCountDto[] = []; // Input for dynamic data
  eventNames: string[] = [];
  ticketCounts: number[] = [];
  public chart?: Chart<'pie', number[], string>;

  constructor() {}

  ngOnChanges(changes: SimpleChanges): void {
    //console.log(this.chartData);
    if (!this.chart) {
      this.createChart();
    }
    if (changes.chartData && !changes.chartData.firstChange) {
      // Update the chart when the input data changes
      this.updateChart();
    }

    if (changes.searchMonth && !changes.searchMonth.firstChange) {
      // Update the chart when the search month input changes
      this.updateChart();
    }
  }

  createChart() {
    this.chart = new Chart('MyChart', {
      type: 'pie',
      data: {
        labels: [],
        datasets: [{
          label: 'Top 10 ' + this.eventType + ' in ' + this.searchMonth,
          data: [],
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
    console.log(this.chart);
  }

  updateChart() {
    this.chart.data.labels =  this.chartData.map(temp => temp.event.title);
    this.chart.data.datasets[0].data = this.chartData.map(temp => temp.boughtCount+2); //TODO remove +2, only for testing purposes cause count = 0
    this.chart.options.plugins.title.text = 'Top 10 ' + this.eventType + ' in ' + this.searchMonth;
    this.chart.update();
  }
}
