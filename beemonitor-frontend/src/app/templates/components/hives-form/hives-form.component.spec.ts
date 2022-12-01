import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HivesFormComponent } from './hives-form.component';

describe('HivesFormComponent', () => {
  let component: HivesFormComponent;
  let fixture: ComponentFixture<HivesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HivesFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HivesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
