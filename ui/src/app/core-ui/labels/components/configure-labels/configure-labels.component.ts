import { Component, OnInit } from '@angular/core';
import { ColorService } from '../../../image/services/color.service';
import { LabelService } from '../../services/label.service';
import { Category, Label } from '../../../../core-model/gen/streampipes-model';

@Component({
  selector: 'sp-configure-labels',
  templateUrl: './configure-labels.component.html',
  styleUrls: ['./configure-labels.component.css']
})
export class ConfigureLabelsComponent implements OnInit {

  constructor(public colorService: ColorService, public labelService: LabelService) { }

  public categories: Category[];
  public selectedCategory: Category;
  public categoryLabels: Label[];

  public editCategory: boolean;

  public noCategoriesAvailable = true;

  ngOnInit(): void {
    this.editCategory = false;
    this.labelService.getCategories().subscribe(res => {
      this.categories = res;
      this.setCategoryAvailable();
    });
  }


  startEditCategory(value) {
    if ('internal_placeholder' !== value.value) {
      this.editCategory = true;
    }

    this.labelService.getLabelsOfCategory(this.selectedCategory).subscribe((res: Label[]) => {
      this.categoryLabels = res;
    });
  }

  endEditCategory() {
    this.selectedCategory = null;
    this.editCategory = false;
  }

  addCategory() {
    const c1 = new Category();
    c1.name = '';

    this.labelService.addCategory(c1).subscribe((res: Category) => {
      this.selectedCategory = res;
      this.editCategory = true;
      this.categories.push(res);
      this.setCategoryAvailable();
    });

    this.categoryLabels = [];
  }

  setCategoryAvailable() {
    if (this.categories.length > 0) {
      this.noCategoriesAvailable = false;
    } else {
      this.noCategoriesAvailable = true;
    }
  }

  updateCategory(newCategoryName) {
    this.selectedCategory.name = newCategoryName;

    this.labelService.updateCategory(this.selectedCategory)
      .subscribe((res: Category) => {
      this.categories = this.categories.filter(obj => obj !== this.selectedCategory);
      this.categories.push(res);
      this.selectedCategory = res;
    });
  }

  deleteCategory() {
    this.labelService.deleteCategory(this.selectedCategory).subscribe();
    this.categories = this.categories.filter(obj => obj !== this.selectedCategory);
    this.endEditCategory();
  }

  addLabel() {
    const label = new Label();
    label.name = '';
    // tslint:disable-next-line:no-bitwise
    label.color = '#' + (Math.random() * 0xFFFFFF << 0).toString(16).padStart(6, '0');
    label.categoryId = this.selectedCategory._id;

    this.labelService.addLabel(label).subscribe((res: Label) => {
      this.categoryLabels.push(res);
    });
  }

  removeLabel(label) {
    this.labelService.deleteLabel(label).subscribe();
    this.categoryLabels = this.categoryLabels.filter(obj => obj !== label);
  }
}