import { computed, Signal, useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, NumberField, NumberFieldElement } from '@vaadin/react-components';
import {
  CustomerDataProvider,
  EmployeeDataProvider,
  ModeOfPaymentDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useEffect, useMemo, useRef } from 'react';
import { CartDetails, InvoiceDetails } from './_ItemCartRC';

const InvoiceBuilderRC = ({
  cartDetails,
  onChange,
}: {
  cartDetails: Signal<CartDetails>;
  onChange: (invDetails: InvoiceDetails) => void;
}) => {
  const { totalNetAmount } = cartDetails.value;
  const customerDataProvider = useMemo(() => CustomerDataProvider, []);
  const employeeDataProvider = useMemo(() => EmployeeDataProvider, []);
  const modeOfPaymentDataProvider = useMemo(() => ModeOfPaymentDataProvider, []);
  const invDetails = useSignal<InvoiceDetails>({
    totalCost: totalNetAmount,
    invDiscount: 0,
    invService: 0,
    paidAmount: 0,
    dueAmount: 0,
  } as InvoiceDetails);

  const discountRef = useRef<NumberFieldElement>(null);
  const serviceRef = useRef<NumberFieldElement>(null);

  const dueAmount = computed<number>(() => {
    const { invDiscount, invService, paidAmount } = invDetails.value;
    return totalNetAmount - (invDiscount * totalNetAmount) / 100 + (invService * totalNetAmount) / 100 - paidAmount;
  });

  function changeState(amount: number) {
    if (amount === 0) return '';
    return amount > 0 ? '(Due)' : '(Return)';
  }

  useEffect(() => {
    const { invDiscount, invService } = invDetails.value;
    const totalCostE = totalNetAmount - (invDiscount * totalNetAmount) / 100 + (invService * totalNetAmount) / 100 || 0;
    onChange({ ...invDetails.value, totalCost: totalCostE });
  }, [invDetails.value, onChange, totalNetAmount]);

  return (
    <div className="h-full w-full flex flex-col gap-2">
      <div className="flex flex-col border gap-2 p-2 rounded-xl">
        <h1 className=" bg-green-600 text-white rounded-lg place-content-center text-center font-bold py-2">
          Bill Information
        </h1>
        <div>
          <div className="flex flex-row items-baseline">
            <span className="flex grow w-full">Customer ID:</span>
            <ComboBox
              className="flex grow w-full"
              placeholder='Search "Customer ID"'
              style={{ '--vaadin-combo-box-overlay-width': '350px' } as React.CSSProperties}
              dataProvider={customerDataProvider}
              itemLabelPath="customerId"
              itemValuePath="id"
              onSelectedItemChanged={(e) => {
                const customer = e.detail.value;
                invDetails.value = { ...invDetails.value, customerId: Number(customer?.id) };
              }}
              renderer={(item) => {
                const { id, customerId, firstName, lastName, phone } = item.item;
                return (
                  <div className="flex flex-row items-center gap-2">
                    <img
                      src={`images/profile/${id !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
                      className="w-8 h-8 rounded-full"
                      alt="not_found"
                    />
                    <div className="flex flex-col">
                      <span className="font-medium">{`${firstName} ${lastName}`}</span>
                      <span className="font-extralight text-sm text-gray-500">{`${customerId} | ${phone}`}</span>
                    </div>
                  </div>
                );
              }}
            />
          </div>
        </div>
        <div>
          <div className="flex flex-row items-baseline">
            <span className="flex grow w-full">Stuff ID:</span>
            <ComboBox
              style={{ '--vaadin-combo-box-overlay-width': '350px' } as React.CSSProperties}
              className="flex grow w-full"
              placeholder='Search "Stuff ID"'
              dataProvider={employeeDataProvider}
              itemLabelPath="employeeId"
              itemValuePath="id"
              onSelectedItemChanged={(e) => {
                const employee = e.detail.value;
                invDetails.value = { ...invDetails.value, employeeId: Number(employee?.id) };
                onChange(invDetails.value);
              }}
              renderer={(item) => {
                const { employeeId, id, firstName, lastName } = item.item;
                return (
                  <div className="flex flex-row items-center gap-2">
                    <img
                      src={`images/profile/${id !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
                      className="w-8 h-8 rounded-full"
                      alt="not_found"
                    />
                    <div className="flex flex-col">
                      <span className="font-medium">{`${firstName} ${lastName}`}</span>
                      <span className="font-extralight text-sm text-gray-500">{`${employeeId}`}</span>
                    </div>
                  </div>
                );
              }}
            />
          </div>
        </div>
      </div>
      <div className="flex flex-col border gap-2 p-2 rounded-xl">
        <h1 className=" bg-green-600 text-white rounded-lg place-content-center text-center font-bold py-2">Invoice</h1>
        <div>
          <ComboBox
            label="Mode Of Payment:"
            className="w-full"
            dataProvider={modeOfPaymentDataProvider}
            itemLabelPath="name"
            itemValuePath="id"
            onSelectedItemChanged={(e) => {
              const payMode = e.detail.value;
              invDetails.value = { ...invDetails.value, paymentMode: Number(payMode?.id) };
            }}
          />
          <div className="flex flex-row items-baseline gap-2 w-full">
            <NumberField
              ref={discountRef}
              label="Discount:"
              className="w-36"
              defaultValue={0}
              min={0}
              max={1000}
              onValueChanged={(e) => {
                const discount = e.detail.value;
                invDetails.value = {
                  ...invDetails.value,
                  invDiscount: Number(discount),
                  dueAmount: dueAmount.value,
                };
              }}
            >
              <span slot="suffix">%</span>
            </NumberField>
            <NumberField
              min={0}
              className="w-full"
              onValueChanged={(e) => {
                if (discountRef.current && totalNetAmount) {
                  const discount = e.detail.value;
                  discountRef.current.value = ((Number(discount) * 100) / totalNetAmount).toString();
                }
              }}
            >
              <span slot="suffix">:amount</span>
            </NumberField>
          </div>
          <div className="flex flex-row items-baseline gap-2 w-full">
            <NumberField
              ref={serviceRef}
              label="Service:"
              min={0}
              max={1000}
              className="w-36"
              defaultValue={0}
              onValueChanged={(e) => {
                const service = e.detail.value;
                invDetails.value = {
                  ...invDetails.value,
                  invService: Number(service),
                };
              }}
            >
              <span slot="suffix">%</span>
            </NumberField>
            <NumberField
              min={0}
              className="w-full"
              onValueChanged={(e) => {
                if (serviceRef.current && totalNetAmount) {
                  const service = e.detail.value;
                  serviceRef.current.value = ((Number(service) * 100) / totalNetAmount).toString();
                }
              }}
            >
              <span slot="suffix">:amount</span>
            </NumberField>
          </div>
          <NumberField
            label="Paid Amount:"
            min={0}
            className="w-full"
            defaultValue={0}
            onValueChanged={(e) => {
              const paidAmount = e.detail.value || 0;
              invDetails.value = {
                ...invDetails.value,
                paidAmount: Number(paidAmount),
              };
            }}
          >
            <span slot="suffix">:amount</span>
          </NumberField>
          <h1 className="bg-green-500/20 text-green-700 font-bold text-center p-1 rounded-lg">{`Change: ${Math.round(Math.abs(dueAmount.value)).toFixed(2)} ${changeState(dueAmount.value)}`}</h1>
        </div>
      </div>
    </div>
  );
};

export default InvoiceBuilderRC;
