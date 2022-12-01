import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HivesDeleteComponent } from './hives-delete.component';

describe('HivesDeleteComponent', () => {
  let component: HivesDeleteComponent;
  let fixture: ComponentFixture<HivesDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HivesDeleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HivesDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
