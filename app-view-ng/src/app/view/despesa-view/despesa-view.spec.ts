import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DespesaView } from './despesa-view';

describe('DespesaView', () => {
  let component: DespesaView;
  let fixture: ComponentFixture<DespesaView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DespesaView],
    }).compileComponents();

    fixture = TestBed.createComponent(DespesaView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
