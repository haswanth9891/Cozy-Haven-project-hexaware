import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchRoomsListComponent } from './search-rooms-list.component';

describe('SearchRoomsListComponent', () => {
  let component: SearchRoomsListComponent;
  let fixture: ComponentFixture<SearchRoomsListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchRoomsListComponent]
    });
    fixture = TestBed.createComponent(SearchRoomsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
