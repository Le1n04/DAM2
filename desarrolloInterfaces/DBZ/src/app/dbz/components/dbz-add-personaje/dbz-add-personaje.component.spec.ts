import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DbzAddPersonajeComponent } from './dbz-add-personaje.component';

describe('DbzAddPersonajeComponent', () => {
  let component: DbzAddPersonajeComponent;
  let fixture: ComponentFixture<DbzAddPersonajeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DbzAddPersonajeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DbzAddPersonajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});