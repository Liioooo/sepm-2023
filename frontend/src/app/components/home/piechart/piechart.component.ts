import { Component, ElementRef, Input, OnChanges, OnDestroy, SimpleChanges, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';
import { EventWithBoughtCountDto } from '../../../dtos/event-with-bought-count-dto';

@Component({
  selector: 'app-piechart',
  templateUrl: './piechart.component.html',
  styleUrls: ['./piechart.component.scss']
})
export class PiechartComponent implements OnChanges, OnDestroy {

  @Input() searchMonth: string = '';
  @Input() eventType: string = '';
  @Input() chartData: EventWithBoughtCountDto[] = [];

  @ViewChild('charRef', { static: true }) chartRef: ElementRef<HTMLCanvasElement>;

  public chart?: Chart<'pie', number[], string>;

  constructor(private router: Router) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.chart) {
      this.createChart();
    }
    if ((changes.chartData && !changes.chartData.firstChange) || (changes.searchMonth && !changes.searchMonth.firstChange)) {
      this.updateChart();
    }
  }

  ngOnDestroy(): void {
    this.chart?.destroy();
  }

  createChart() {
    this.chart = new Chart(this.chartRef.nativeElement, {
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
            text: 'Top 10 ' + this.eventType + 's in ' + this.searchMonth,
            font: {
              size: 34,
              weight: 'bold',
              family: '\'Helvetica Neue\', \'Helvetica\', \'Arial\', sans-serif'
            },
            padding: {
              top: 40,
              bottom: 40
            },
            color: '#000000'
          },
          legend: {
            display: true,
            labels: {
              font: {
                size: 14,
                family: '\'Helvetica Neue\', \'Helvetica\', \'Arial\', sans-serif'
              }
            }
          },
          tooltip: {
            callbacks: {
              title: () => 'Click here to get your ticket',
              label: (context: any) => {
                const label = context.label || '';
                return `${label}`;
              }
            }
          }
        },
        onClick: (event: any, elements: any[]) => {
          if (elements.length > 0) {
            const clickedIndex = elements[0].index;
            const eventId = this.chartData[clickedIndex].event.id;
            this.router.navigate(['/events', eventId]);
          }
        }
      }
    });
  }

  updateChart() {
    this.chart.data.labels = this.chartData.map(temp => temp.event.title);
    this.chart.data.datasets[0].data = this.chartData.map(temp => temp.boughtCount);
    this.chart.options.plugins.title.text = 'Top 10 ' + this.eventType + 's in ' + this.searchMonth;
    this.chart.update();
  }
}
