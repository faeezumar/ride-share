import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SseComponentComponent } from './sse-component.component';

describe('SseComponentComponent', () => {
  let component: SseComponentComponent;
  let fixture: ComponentFixture<SseComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SseComponentComponent]
    });
    fixture = TestBed.createComponent(SseComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
