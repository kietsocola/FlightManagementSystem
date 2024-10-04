import React from 'react';
import './Form.css'; // Tạo một tệp CSS riêng cho form nếu cần

const PersonalInformationForm = () => {
    return (
        <div className="form-container">
            <h2 className="header-form"> Personal Information </h2> <hr className="header-line" />{' '}
            <form>
                <div className="full-name">
                    <label className="lable-form"> Full Name </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter first name" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Last Name </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter last name" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Email Address </label>{' '}
                    <input
                        className="input-form"
                        type="email"
                        placeholder="   Enter email address"
                    />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Username </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter username" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Phone No </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter phone number" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> City </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter your city" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Country Name </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter country name" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Zip Code </label>{' '}
                    <input className="input-form" type="text" placeholder="   Enter zip code" />
                </div>{' '}
                <div className="full-name">
                    <label className="lable-form"> Bio </label>{' '}
                    <select className="input-form">
                        <option> Pacific Standard Time </option>{' '}
                        <option> Eastern Standard Time </option>{' '}
                        {/* Thêm các lựa chọn múi giờ khác */}{' '}
                    </select>{' '}
                </div>{' '}
                <textarea className="description" placeholder="   Write a short introduction">
                    {' '}
                </textarea>{' '}
                <div className="full-name">
                    <label className="lable-form"> Timezone </label>{' '}
                    <select className="input-form">
                        <option> Pacific Standard Time </option>{' '}
                        <option> Eastern Standard Time </option>{' '}
                        {/* Thêm các lựa chọn múi giờ khác */}{' '}
                    </select>{' '}
                </div>{' '}
                <div className="full-name">
                    <button className="submit-form" type="submit">
                        {' '}
                        Submit{' '}
                    </button>{' '}
                </div>{' '}
            </form>{' '}
        </div>
    );
};

export default PersonalInformationForm;
