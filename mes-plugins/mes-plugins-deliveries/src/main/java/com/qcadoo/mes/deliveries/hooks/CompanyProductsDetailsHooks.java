package com.qcadoo.mes.deliveries.hooks;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.qcadoo.mes.basic.constants.ProductFields;
import com.qcadoo.mes.deliveries.constants.CompanyProductFields;
import com.qcadoo.mes.technologies.constants.ProductComponentFields;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.api.components.LookupComponent;
import com.qcadoo.view.constants.QcadooViewConstants;

@Service
public class CompanyProductsDetailsHooks {

    private static final String L_UNIT = "unit";

    public void onBeforeRenderForProduct(final ViewDefinitionState view) {
        FormComponent productCompanyForm = (FormComponent) view.getComponentByReference(QcadooViewConstants.L_FORM);
        FieldComponent unitField = (FieldComponent) view.getComponentByReference(L_UNIT);

        Entity productCompany = productCompanyForm.getEntity();
        Entity product = productCompany.getBelongsToField(ProductComponentFields.PRODUCT);

        if (Objects.nonNull(product)) {
            unitField.setFieldValue(product.getStringField(ProductFields.UNIT));
            unitField.requestComponentUpdateState();
        }
    }

    public void onBeforeRenderForCompany(final ViewDefinitionState view) {
        LookupComponent productLookupComponent = (LookupComponent) view.getComponentByReference(CompanyProductFields.PRODUCT);
        FieldComponent unitField = (FieldComponent) view.getComponentByReference(L_UNIT);

        if (productLookupComponent.isEmpty()) {
            unitField.setFieldValue("");
        } else {
            Entity product = productLookupComponent.getEntity();

            if (Objects.nonNull(product)) {
                unitField.setFieldValue(product.getStringField(ProductFields.UNIT));
            }
        }

        unitField.requestComponentUpdateState();
    }

}
