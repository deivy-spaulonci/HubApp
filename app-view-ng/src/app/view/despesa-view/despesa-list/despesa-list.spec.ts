import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DespesaList } from './despesa-list';

describe('DespesaList', () => {
  let component: DespesaList;
  let fixture: ComponentFixture<DespesaList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DespesaList],
    }).compileComponents();

    fixture = TestBed.createComponent(DespesaList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
