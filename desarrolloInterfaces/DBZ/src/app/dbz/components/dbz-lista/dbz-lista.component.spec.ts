import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DbzListaComponent } from './dbz-lista.component';

describe('DbzListaComponent', () => {
  let component: DbzListaComponent;
  let fixture: ComponentFixture<DbzListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DbzListaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DbzListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
